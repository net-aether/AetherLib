package net.aether.lib.data;

import java.util.List;

/**
 * A synchronized implementation of {@link Queue}<br>
 * Behaves exactly the same as {@link SimpleQueue}
 * 
 * @author Cheos
 * @see {@link SimpleQueue}
 */
public class SynchronizedQueue<T> extends SimpleQueue<T> {
	public SynchronizedQueue(          ) { super(  ); }
	public SynchronizedQueue(List<T> of) { super(of); }
	
	@Override public synchronized boolean insert(T t, int idx) { return super.insert(t, idx); }
	@Override public synchronized boolean push  (T t         ) { return super.push  (t     ); }
	@Override public synchronized T       pop   (            ) { return super.pop   (      ); }
	@Override public synchronized T       get   (     int idx) { return super.get   (   idx); }
}
