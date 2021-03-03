package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;
import net.aether.lib.lambda.Provider;

/**
 * Class for wrapping any object.
 * 
 * @author Cheos
 */
@Since(V0_0_1)
public class Wrapper<T> implements Provider<T> {
	private T wrapped;
	private final boolean fin;
	
	private Wrapper(T wrap, boolean fin) {
		this.wrapped = wrap;
		this.fin = fin;
	}
	
	/**
	 * @param <T>
	 * @param t
	 * @return A new {@link Wrapper} wrapping <b>t</b>
	 */
	public static <T> Wrapper<T> wrap(T t) {
		return new Wrapper<>(t, false);
	}
	
	/**
	 * 
	 * @param <T>
	 * @param t
	 * @return A new {@link Wrapper} wrapping final <b>t</b>
	 */
	public static <T> Wrapper<T> wrapFinal(T t) {
		return new Wrapper<>(t, true);
	}
	
	/**
	 * Sets (overrides) the wrapped element by this {@link Wrapper}
	 * 
	 * @param t
	 * @return Itself for convenience
	 */
	public Wrapper<T> set(T t) {
		if(fin) throw new UnsupportedOperationException("Cannot modify final wrapped objects!");
		
		this.wrapped = t;
		return this;
	}
	
	/**
	 * @return The element wrapped by this {@link Wrapper}
	 */
	@Override
	public T get() {
		return wrapped;
	}

	@Override
	public String toString() {
		return get().toString();
	}
}
