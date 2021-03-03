package net.aether.lib.debug;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

/**
 * This class represents, as it's name suggests, a very primitive timer
 * 
 * @author Cheos
 * @author Kilix
 */
@Since(V0_0_1)
public class PrimitiveTimer {
		
	private long start = System.currentTimeMillis();
	
	/**
	 * Note: this method resets the reference time of this timer. Use {@link #peek()} if you want to receive a time difference without interfering.
	 * @return the time passed since the last call to {@link #mark()} or, if this method has never been called before, to the creation of this timer, in milliseconds
	 */
	public long mark() {
		long now = System.currentTimeMillis();
		long diff = now - start;
		start = now;
		
		return diff;
	}
	
	/**
	 * @return the time passed since the last call to {@link #mark()} or, if this method has never been called before, to the creation of this timer, in milliseconds
	 */
	public long peek() {
		return System.currentTimeMillis() - start;
	}
}
