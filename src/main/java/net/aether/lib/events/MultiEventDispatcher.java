package net.aether.lib.events;

import net.aether.lib.lambda.Consumer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Extended for of SimpleEventDispatcher, that stores a List of listeners, instead of just one<br>
 * When {@link #unregister(Class)} is called, all of the listeners will be deleted.
 */
public class MultiEventDispatcher extends SimpleEventDispatcher {
	
	HashMap<Class<?>, ArrayList<Consumer<Object>>> registeredListeners = new HashMap<>();
	
	@Override
	protected void _dispatch(Class<?> eventType, Object event) {
		// don't try to dispatch the event, when no one is listening
		if (! registeredListeners.containsKey(eventType)) return;
		
		// loop through all the listeners, and send them the event
		registeredListeners.get(eventType).forEach(o -> o.call(event));
	}
	
	@Override
	protected <T> void _register(Class<T> eventType, Consumer<T> listener) {
		registeredListeners.putIfAbsent(eventType, new ArrayList<>());
		registeredListeners.get(eventType).add(listener.forObject());
	}
	
	@Override
	protected void _unregister(Class<?> eventType) {
		registeredListeners.remove(eventType);
	}
	
	@Override
	protected Iterable<Class<?>> _getApplicableListeners(Object event) {
		ArrayList<Class<?>> validListeners = new ArrayList<>();
		registeredListeners.keySet().forEach((Class<?> eventType) -> {
			if (eventType.isInstance(event)) validListeners.add(eventType);
		});
		return validListeners;
	}
}
