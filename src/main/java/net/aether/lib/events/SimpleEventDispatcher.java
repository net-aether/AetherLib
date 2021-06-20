package net.aether.lib.events;

import net.aether.lib.annotation.EventListener;
import net.aether.lib.lambda.Consumer;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

public class SimpleEventDispatcher implements EventDispatcher {
	private final HashMap<Class<?>, Consumer<Object>> registeredListeners = new HashMap<>();
	
	@Override
	public <T> void register(Class<T> eventType, Consumer<T> listener) {
		// while this does not need to be an extra method call,
		// it's clearer what to override to change the basic functionality of the Dispatcher,
		// so that any overrides do not necessarily need to re-write the reflection
		_register(eventType, listener);
	}
	
	@Override
	public void registerClass(Class<?> listenerClass) {
		// loop over each method in the given class
		for (Method method : listenerClass.getMethods()) {
			// check for the annotation, and that the method is static
			if (method.isAnnotationPresent(EventListener.class) && Modifier.isStatic(method.getModifiers())) {
				// check for signature (Consumer takes 1 parameter and returns nothing)
				if (method.getParameterCount() == 1 && method.getReturnType() == Void.TYPE) {
					// if all the checks so far got passed, register the method
					_register(method.getParameterTypes()[0], (obj) -> {
						// simply ignore if anything went wrong
						try { method.invoke(null, obj); } catch (Exception ignored) {}
					});
				}
			}
		}
	}
	
	@Override
	public void registerObject(Object listenerObject) {
		// simply get the class, and re-use the code from registerClass
		Class<?> listenerClass = listenerObject.getClass();
		// loop over each method in the given class
		for (Method method : listenerClass.getMethods()) {
			// check for the annotation, and that the method is dynamic
			if (method.isAnnotationPresent(EventListener.class) && ! Modifier.isStatic(method.getModifiers())) {
				// check for signature (Consumer takes 1 parameter and returns nothing)
				if (method.getParameterCount() == 1 && method.getReturnType() == Void.TYPE) {
					// if all the checks so far got passed, register the method
					_register(method.getParameterTypes()[0], (obj) -> {
						// simply ignore if anything went wrong, but this time invoke the method with the object
						try { method.invoke(listenerObject, obj); } catch (Exception ignored) {}
					});
				}
			}
		}
	}
	
	@Override
	public void triggerEvent(Object event) {
		Iterable<Class<?>> validListeners = _getApplicableListeners(event);
		for (Class<?> listener : validListeners) {
			_dispatch(listener, event);
		}
	}
	
	@Override
	public void unregister(Class<?> eventType) { _unregister(eventType); }
	
	/**
	 * Dispatches an event, and sends it to the listener listening for the supplied type
	 * To modify how events get dispatched, override this method
	 * @param eventType the type of event
	 * @param event the event object to send to the eventType
	 */
	protected void _dispatch(Class<?> eventType, Object event) { registeredListeners.get(eventType).forObject().call(event); }
	
	/**
	 * Registers a listener that awaits events of the supplied type<br>
	 * To modify how events get registered, override this method.
	 *
	 * @param eventType the type of event to register a new listener for
	 * @param listener the new listener
	 */
	protected <T> void _register(Class<T> eventType, Consumer<T> listener) { registeredListeners.put(eventType, listener.forObject()); }
	
	/**
	 * Unregisters the listener associated with the supplied type of event<br>
	 * To modify how events get unregistered, override this method
	 * @param eventType the type of event to unregister the listeners for
	 */
	protected void _unregister(Class<?> eventType) { registeredListeners.remove(eventType); }

	protected Iterable<Class<?>> _getApplicableListeners(Object event) {
		ArrayList<Class<?>> validListeners = new ArrayList<>();
		registeredListeners.keySet().forEach((Class<?> eventType) -> {
			if (eventType.isInstance(event)) validListeners.add(eventType);
		});
		return validListeners;
	}
}
