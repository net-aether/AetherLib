package net.aether.lib.data;

import net.aether.lib.lambda.Consumer;
import net.aether.lib.lambda.Provider;

/**
 * 
 * @author Cheos, Kilix
 *
 * @param <T>
 */
public final class Promise<T> implements Provider<T> {
	
	private Consumer<T> 		consumer;
	private Consumer<Throwable> onError;
	
	private Thread 				thread;
	private volatile T 			result;
	
	public Promise(Provider<? extends T> provider) {
		thread = new Thread(() -> {
			result = provider.get();
			if (consumer!= null) consumer.call(result);
		});
		thread.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> { if (onError != null) onError.call(throwable); });
		thread.start();
	}
	
	/**
	 * Awaits the result
	 */
	public T get() { return get(0); };
	/**
	 * Awaits the result, or times out after the supplied milliseconds have passed, returning null;
	 * @param millis
	 * @return
	 */
	public T get(long millis) {
		try {
			thread.join(millis);
		} catch (Exception e) { if (onError != null) onError.call(e); }
		return result;
	}
	
	/**
	 * Registers a consumer to be served the value as soon as it is available
	 * @param consumer
	 */
	public synchronized Promise<T> then(Consumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}
	/**
	 * Registers a consumer to be served exceptions that were encountered
	 * @param e
	 */
	public synchronized Promise<T> error(Consumer<Throwable> onError) {
		this.onError = onError;
		return this;
	}
	/**
	 * Interrupts the underlying thread
	 * @return
	 * @throws InterruptedException
	 */
	public synchronized Promise<T> interrupt() throws InterruptedException {
		thread.interrupt();
		return this;
	}
	/**
	 * Join with the underlying thread
	 * @throws InterruptedException
	 */
	public synchronized Promise<T> join() throws InterruptedException {
		return join(0);
	}
	/**
	 * Joins with the underlying thread
	 * @param millis
	 * @throws InterruptedException
	 */
	public synchronized Promise<T> join(long millis) throws InterruptedException {
		thread.join(millis);
		return this;
	}
	
	
	
}
