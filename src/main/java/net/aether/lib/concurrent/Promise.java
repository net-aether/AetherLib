package net.aether.lib.concurrent;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * 
 * @author Cheos
 * @author Kilix
 *
 * @param <T>
 */
@Since(V0_0_1)
public class Promise<T> implements Supplier<T> {
	protected static int counter = 0;
	
	protected Consumer<T> consumer;
	protected Consumer<Throwable> onError;
	
	protected Thread              thread;
	protected volatile T          result;
	
	protected boolean             fulfilled, interrupting;
	
	protected Promise() { }
	
	public Promise(Supplier<? extends T> supplier) {
		thread = new Thread(() -> {
			result = supplier.get();
			if (consumer!= null) consumer.accept(result);
			fulfilled = true;
		}, String.format("Promise#%08x", counter++));
		thread.setUncaughtExceptionHandler((Thread thread, Throwable throwable) -> { if (onError != null) onError.accept(throwable); });
		thread.start();
	}
	
	/**
	 * Awaits the result
	 * @return the result
	 */
	@Override
	public T get() { return get(0L); };
	
	/**
	 * Awaits the result, or times out after the supplied milliseconds have passed, returning null;
	 * @param millis the time in milliseconds
	 * @return the result
	 */
	public T get(long millis) {
		try {
			thread.join(millis);
		} catch (Exception e) { if (onError != null) onError.accept(e); }
		return result;
	}
	
	/**
	 * Registers a consumer to be served the value as soon as it is available
	 * @param consumer a consumer of the final value
	 * @return itself
	 */
	public synchronized Promise<T> then(Consumer<T> consumer) {
		this.consumer = consumer;
		return this;
	}
	
	/**
	 * Registers a consumer to be served exceptions that were encountered
	 * @param onError an error handler
	 * @return itself
	 */
	public synchronized Promise<T> error(Consumer<Throwable> onError) {
		this.onError = onError;
		return this;
	}
	
	/**
	 * Interrupts the underlying thread
	 * @return itself
	 */
	public synchronized Promise<T> interrupt() {
		if (interrupting) return this;
		thread.interrupt();
		return this;
	}
	
	/**
	 * Waits until the Promise has been fulfilled or was interrupted
	 * @return itself
	 * @throws InterruptedException if any thread interrupts the current thread
	 */
	public Promise<T> await() throws InterruptedException {
		return await(0L);
	}
	
	/**
	 * Waits until:<ul>
	 * <li> The Promise has been fulfilled</li>
	 * <li> The Promise was interrupted</li>
	 * <li> The Promise was cancelled</li>
	 * <li> The Timeout has been reached</li>
	 * </ul>
	 * @param millis the time in milliseconds
	 * @return itself
	 * @throws InterruptedException if any thread interrupts the current thread
	 */
	public Promise<T> await(long millis) throws InterruptedException {
		thread.join(millis);
		return this;
	}
	
	/**
	 * Interrupts this {@link Promise Promises} thread until it is no longer alive.<br>
	 * This will likely result in Exceptions and null-values when e.g. the executing Thread is waiting for a result (e.g. from a Stream)
	 */
	public void interruptUntilEnd() {
		if (interrupting) return;
		interrupting = true;
		
		new Thread(() -> {
			while (thread.isAlive()) {
				try {
					thread.join(500L);
					thread.interrupt();
				} catch (InterruptedException e) {}
			}
		}, thread.getName() + "@ISlowlyAwaitMyParentThreadsDeath").start();
	}
	
	/**
	 * Stops the executing Thread.<br>
	 * This action is inherently unsafe! {@link #get get} will always be null and {@link #then then} will not be called!
	 * @see {@link Thread#stop()}
	 */
	@Deprecated
	public void cancel() { thread.stop(); }
	
	/**
	 * @return whether or not this promise has been fulfilled
	 */
	public boolean isFulfilled() { return fulfilled; }
	
	
	
	/**
	 * This class represents a faked promise which does not utilize additional threads
	 * 
	 * @author Cheos
	 */
	@Since(V0_0_1)
	public static class Fake<T> extends Promise<T> {

		public Fake(Supplier<? extends T> supplier) {
			super();
			result = supplier.get();
			if (consumer!= null) consumer.accept(result);
			fulfilled = true;
			interrupting = true;
		}
		
		@Override
		public T get(long millis) { return result; }
		
		@Override
		public synchronized Promise<T> then(Consumer<T> consumer) {
			consumer.accept(result);
			return this;
		}
		
		@Override
		public Promise<T> await(long millis) throws InterruptedException { return this; }
		
		@Override
		public synchronized void cancel() { }
	}
}
