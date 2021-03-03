package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import net.aether.lib.annotation.Since;
import net.aether.lib.annotation.WIP;

/**
 * Wraps an Object, and allows save access to it's methods.<br>
 * If a method is not defined, the default value will be returned.
 * @author Kilix
 *
 */
@Since(V0_0_1)
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
	
	@WIP
	public Object call(Method method, Object... arguments) {
		return Void.INSTANCE;
	}
}
