package net.aether.lib.events;

import net.aether.lib.annotation.EventListener;
import net.aether.lib.lambda.Consumer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

public interface EventDispatcher {
	
	/**
	 * Registers a single Consumer as an EventListener.
	 * @param listener the recipient of the event
	 */
	void register(Consumer<?> listener);
	
	/**
	 * Searches the supplied class, and registers all methods, with a valid signature and an {@link EventListener} annotation<br>
	 * If there are multiple valid listeners, only the last one processed will be registered
	 * @param listenerClass the class to search for valid event listeners
	 */
	void register(Class<?> listenerClass);
	
	/**
	 * Searches the supplied Object, and registers all methods, with a valid signature and an {@link EventListener} annotation<br>
	 * If there are multiple valid listeners, only the last one processed will be registered
	 * @param listenerObject the object to search for valid event listeners
	 */
	void register(Object listenerObject);
	
	/**
	 * Triggers an event, which will be sent to any registered listeners that are listening to the event's class
	 * @param event the event to relay
	 */
	void triggerEvent(Object event);
	
	/**
	 * Unregisters the listener that listens for the supplied class.
	 * @param eventType the class for which to unregister the listeners
	 */
	void unregister(Class<?> eventType);
	
}
