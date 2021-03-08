package net.aether.lib.misc;

import java.util.Date;

/**
 * Offers a simple timer
 * @author Kilix
 *
 * @see #mark
 * @see #peek
 */
public class SimpleTimer {
		
	private long start = new Date().getTime();
	
	/**
	 * Resets the timer and
	 * @return the millisecond difference between the start (or last mark) and the time at the method call.
	 */
	public long mark() {
		long now = new Date().getTime();
		long diff = now - start;
		start = now;
		
		return diff;
	}
	
	/**
	 * @return the millisecond difference between the start (or last mark) and the time at the method call.
	 */
	public long peek() {
		return new Date().getTime() - start;
	}
	
}
