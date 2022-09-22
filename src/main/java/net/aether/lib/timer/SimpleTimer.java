package net.aether.lib.timer;

/**
 * Offers a simple timer
 * @author Kilix
 * @author Cheos (improvements)
 *
 * @see #mark
 * @see #peek
 */
public class SimpleTimer {
		
	private long start = System.currentTimeMillis();
	
	/**
	 * Resets the timer and
	 * @return the millisecond difference between the start (or last mark) and the time at the method call.
	 */
	public long mark() {
		long now = System.currentTimeMillis();
		long diff = now - start;
		start = now;
		
		return diff;
	}
	
	/**
	 * @return the millisecond difference between the start (or last mark) and the time at the method call.
	 */
	public long peek() {
		return System.currentTimeMillis() - start;
	}
	
}
