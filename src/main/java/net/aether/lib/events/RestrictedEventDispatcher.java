package net.aether.lib.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RestrictedEventDispatcher extends MultiEventDispatcher {

	private final List<String> allowedClasses;
	
	public RestrictedEventDispatcher(Class<?>... allowedClasses) {
		this.allowedClasses = Arrays.stream(allowedClasses)
				.map(Class::getName)
				.collect(Collectors.toList());
		
		this.allowedClasses.add(Thread.currentThread().getStackTrace()[2].getClassName());
	}
	
	@Override
	protected void dispatch(Class<?> eventType, Object event) {
		// check if the method was called by one of the allowd classes
		if (allowedClasses.contains(Thread.currentThread().getStackTrace()[3].getClassName())) {
			super.dispatch(eventType, event);
		} else {
			System.err.println("Unallowed call from: " + Thread.currentThread().getStackTrace()[3].getClassName());
		}
	}
}
