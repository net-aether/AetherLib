package net.aether.lib.concurrent;

import net.aether.lib.lambda.Consumer;
import net.aether.lib.lambda.Provider;

/**
 * 
 * @author Cheos, Kilix
 *
 * @param <T>
 */
public final class Promise<T> implements Provider<T> {
	
	private static int counter = 0;
	
	private Consumer<T> 		consumer;
	private Consumer<Throwable> onError;
	
	private Thread 				thread;
	private volatile T 			result;
	
	public Promise(Provider<? extends T> provider) {
		thread = new Thread(() -> {
			result = provider.get();
			if (consumer!= null) consumer.call(result);
		}, String.format("Promise#%08x", counter++));
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
	 * Waits until the Promise has been fulfilled or was interrupted
	 * @throws InterruptedException
	 */
	public synchronized Promise<T> await() throws InterruptedException {
		return await(0);
	}
	/**
	 * Waits until:<ul>
	 * <li> The Promise has been fulfilled</li>
	 * <li> The Promise was interrupted</li>
	 * <li> The Promise was cancelled</li>
	 * <li> The Timeout has been reached</li>
	 * </ul>
	 * @param millis
	 * @throws InterruptedException
	 */
	public synchronized Promise<T> await(long millis) throws InterruptedException {
		thread.join(millis);
		return this;
	}
	/**
	 * accelerates execution by skipping all wait times.<br>
	 * This will likely result in Exceptions and null-values when e.g. the executing Thread is waiting for a result (e.g. from a Stream)
	 */
	public synchronized void accelerate() {
		new Thread(() -> {
			while (thread.isAlive()) {
				try {
					thread.join(500);
					thread.interrupt();
				} catch (InterruptedException e) {}
			}
		}, thread.getName() + "@accelerator").start();
	}
	/**
	 * Stops the executing Thread.<br>
	 * This action is not safe! {@link #get get} will always be null and {@link #then then} will not be called!
	 */
	@Deprecated
	public synchronized void cancel() { thread.stop(); }
}
