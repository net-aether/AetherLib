package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.util.*;

import net.aether.lib.annotation.Since;

@Since(V0_0_1)
public class AetherIterator<T> implements ListIterator<T> {

	private T[] values;
	private int cursor = 0, prevIndex = 0;
	
	@SuppressWarnings("unchecked")
	public AetherIterator(List<T> t) { values = (T[]) t.toArray(); }
	
	public AetherIterator(T[] t) { values = t; }
	
	@Override
	public boolean hasNext() { return values.length > cursor; }
	@Override
	public T next() {
		prevIndex = cursor;
		return hasNext() ? values[cursor++] : values[values.length];
	}
	@Override
	public boolean hasPrevious() { return cursor > 0; }
	@Override
	public T previous() {
		prevIndex = cursor;
		
		if (hasPrevious()) cursor--; // don't subtract from the index if we are at the beginning
		T out = values[cursor];
		return out;
	}
	@Override
	public int nextIndex() { return cursor < values.length - 1 ? cursor + 1 : values.length; }
	@Override
	public int previousIndex() { return cursor - 1; }
	@Override
	public void set(T e) {
		if (prevIndex < 0) values[0] = e;
		else if (prevIndex >= values.length) values[values.length] = e;
		else values[prevIndex] = e;
	}
	@Override
	public void add(T e) {
		T[] newValues = Arrays.copyOf(values, values.length + 1);
		
		for (int i = 0; i < cursor; i++) newValues[i] = values[i];
		newValues[cursor] = e;
		for (int i = cursor; i < values.length; i++) newValues[i + 1] = values[i];
		
		cursor++;
		prevIndex++;
		values = newValues;
	}
	@Override
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
