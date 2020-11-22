package net.aether.lib.data;

import java.util.Iterator;
import java.util.List;

public class AetherIterator<T> implements Iterator<T> {

	private final T[] values;
	private int counter = 0;
	
	@SuppressWarnings("unchecked")
	public AetherIterator(List<T> t) {
		values = (T[]) t.toArray();
	}
	
	public AetherIterator(T[] t) {
		values = t;
	}
	
	@Override
	public boolean hasNext() {
		return values.length > counter;
	}

	@Override
	public T next() {
		T out = values[counter];
		counter++;
		return out;
	}
	
}
