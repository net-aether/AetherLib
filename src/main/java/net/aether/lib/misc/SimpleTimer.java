package net.aether.lib.debug;

import java.util.Date;

public class PrimitiveTimer {
		
	private long start = new Date().getTime();
	
	public long mark() {
		long now = new Date().getTime();
		long diff = now - start;
		start = now;
		
		return diff;
	}
	
	public long peek() {
		return new Date().getTime() - start;
	}
	
}
