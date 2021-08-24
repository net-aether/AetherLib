package net.aether.lib.events;

import net.aether.lib.annotation.EventListener;
import net.aether.lib.lambda.Consumer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class SimpleEventDispatcher implements EventDispatcher {
	private final HashMap<Class<?>, Consumer<?>> registeredListeners = new HashMap<>();
	
	public void register(Consumer<?> listener) {
		Class<?> eventType = null;
		// loop over all methods in the supplied listener
		for (Method method : listener.getClass().getMethods()) {
			// look for "call" methods, with 1 parameter and void as a return type
			if ("call".equals(method.getName()) && method.getParameterCount() == 1 && method.getReturnType() == Void.TYPE) {
				// get the class of the parameter, and store it
				eventType = method.getParameterTypes()[0];
			}
		}
		
		// while this does not need to be a seperate method,
		// it's clearer what to override to change the basic functionality of the Dispatcher,
		// so that any overrides do not necessarily need to re-write the reflection
		if (eventType != null) registerInternal(eventType, listener);
	}
	
	@Override
	public void register(Class<?> listenerClass) {
		// loop over each method in the given class
		for (Method method : listenerClass.getDeclaredMethods()) {
			// check for the annotation, and that the method is static
			if (method.isAnnotationPresent(EventListener.class) && Modifier.isStatic(method.getModifiers())) {
				// check for signature (Consumer takes 1 parameter and returns nothing)
				if (method.getParameterCount() == 1 && method.getReturnType() == Void.TYPE) {
					// if all the checks so far got passed, register the method
					registerInternal(method.getParameterTypes()[0], (obj) -> {
						// simply ignore if anything went wrong
						try { method.invoke(null, obj); } catch (Exception ignored) {}
					});
				}
			}
		}
	}
	
	@Override
	public void register(Object listenerObject) {
		// simply get the class, and re-use the code from registerClass
		Class<?> listenerClass = listenerObject.getClass();
		// loop over each method in the given class
		for (Method method : listenerClass.getMethods()) {
			// check for the annotation, and that the method is dynamic
			if (method.isAnnotationPresent(EventListener.class) && ! Modifier.isStatic(method.getModifiers())) {
				// check for signature (Consumer takes 1 parameter and returns nothing)
				if (method.getParameterCount() == 1 && method.getReturnType() == Void.TYPE) {
					// if all the checks so far got passed, register the method
					registerInternal(method.getParameterTypes()[0], (obj) -> {
						// simply ignore if anything went wrong, but this time invoke the method with the object
						try { method.invoke(listenerObject, obj); } catch (Exception ignored) {}
					});
				}
			}
		}
	}
	
	@Override
	public void triggerEvent(Object event) {
		Iterable<Class<?>> validListeners = getApplicableListeners(event);
		for (Class<?> listener : validListeners) {
			dispatch(listener, event);
		}
	}
	
	@Override
	public void unregister(Class<?> eventType) { unregisterInternal(eventType); }
	
	/**
	 * Dispatches an event, and sends it to the listener listening for the supplied type
	 * To modify how events get dispatched, override this method
	 * @param eventType the type of event
	 * @param event the event object to send to the eventType
	 */
	protected void dispatch(Class<?> eventType, Object event) { registeredListeners.get(eventType).generic().call(event); }
	
	/**
	 * Registers a listener that awaits events of the supplied type<br>
	 * To modify how events get registered, override this method.
	 *
	 * @param eventType the type of event to register a new listener for
	 * @param listener the new listener
	 */
	protected void registerInternal(Class<?> eventType, Consumer<?> listener) { registeredListeners.put(eventType, listener); }
	
	/**
	 * Unregisters the listener associated with the supplied type of event<br>
	 * To modify how events get unregistered, override this method
	 * @param eventType the type of event to unregister the listeners for
	 */
	protected void unregisterInternal(Class<?> eventType) { registeredListeners.remove(eventType); }

	protected Iterable<Class<?>> getApplicableListeners(Object event) {
		ArrayList<Class<?>> validListeners = new ArrayList<>();
		registeredListeners.keySet().forEach((Class<?> eventType) -> {
			if (eventType.isInstance(event)) validListeners.add(eventType);
		});
		return validListeners;
	}
}
