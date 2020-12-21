package net.aether.lib.data;

import java.util.*;

public class AetherIterator<T> implements ListIterator<T> {

	private T[] values;
	private int cursor = 0, prevIndex = 0;
	
	@SuppressWarnings("unchecked")
	public AetherIterator(List<T> t) { values = (T[]) t.toArray(); }
	
	public AetherIterator(T[] t) { values = t; }
	
	public boolean hasNext() { return values.length > cursor; }
	public T next() {
		prevIndex = cursor;
		return hasNext() ? values[cursor++] : values[values.length];
	}
	public boolean hasPrevious() { return cursor > 0; }
	public T previous() {
		prevIndex = cursor;
		
		if (hasPrevious()) cursor--; // don't subtract from the index if we are at the beginning
		T out = values[cursor];
		return out;
	}
	public int nextIndex() { return cursor < values.length - 1 ? cursor + 1 : values.length; }
	public int previousIndex() { return cursor - 1; }
	public void set(T e) {
		if (prevIndex < 0) values[0] = e;
		else if (prevIndex >= values.length) values[values.length] = e;
		else values[prevIndex] = e;
	}
	public void add(T e) {
		T[] newValues = Arrays.copyOf(values, values.length + 1);
		
		for (int i = 0; i < cursor; i++) newValues[i] = values[i];
		newValues[cursor] = e;
		for (int i = cursor; i < values.length; i++) newValues[i + 1] = values[i];
		
		cursor++;
		prevIndex++;
		values = newValues;
	}
	public void remove() {
		T[] newValues = Arrays.copyOf(values, values.length - 1);
		for (int i = 0; i < prevIndex; i++) newValues[i] = values[i];
		for (int i = prevIndex; i < values.length; i++) newValues[i - 1] = values[i];
		values = newValues;
		
		cursor--;
		prevIndex--;
	}
	
	@Override
	public String toString() {
		return Arrays.asList(values) + " cursor is between: " + (cursor - 1) + " and " + cursor;
	}
	
}
