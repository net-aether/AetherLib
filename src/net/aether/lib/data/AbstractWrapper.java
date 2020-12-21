package net.aether.lib.data;

import java.lang.reflect.*;

/**
 * Wraps an Object, and allows save access to it's methods.<br>
 * If a method is not defined, the default value will be returned.
 * @author Kilix
 *
 */
public class AbstractWrapper<T> {

	private final T value;
	
	public AbstractWrapper(Class<? extends T> clazz) throws InstantiationException, IllegalAccessException {
		value = clazz.newInstance();
	}
	
	public AbstractWrapper(Class<? extends T> clazz, Object... arguments) {
		Class<?>[] args = new Class<?>[arguments.length];
		for (int i = 0; i < arguments.length; i++) args[i] = arguments[i].getClass();
		
		for (Constructor<?> constructor :  clazz.getConstructors()) {
			Class<?>[] params = constructor.getParameterTypes();
			if (params == args) {
				
			}
			
		}
		value = null;
	}
	
	public Object call(Method method, Object... arguments) {		
		return Void.INSTANCE;
	}
	
}
