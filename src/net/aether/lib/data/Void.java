package net.aether.lib.data;

import java.util.*;

/**
 * Used to avoid {@link NullPointerException}
 * @author Kilix
 *
 */
public final class Void implements 
	Comparable<Void>, 
	Iterable<Void>, 
	Iterator<Void>,
	List<Void> {
	
	/**
	 * Void == Void, no constructor needed.
	 */
	private Void() {}
	/**
	 * Literally just new Void(). You could just use that instead... 
	 */
	public static final Void INSTANCE = new Void();
	public static final Void[] AS_ARRAY = new Void[] { INSTANCE };
	public static final ListIterator<Void> AS_LIST_ITERATOR = new AetherIterator<Void>(AS_ARRAY);
	
	/**
	 * @return "null"
	 */
	public String toString() { return ""; }
	/**
	 * @return {@link Void#INSTANCE}
	 */
	protected Void clone() { return INSTANCE; }
	/**
	 * @return {@link #INSTANCE}.hashCode()
	 */
	public int hashCode() { return 0; }
	/**
	 * @return true if both objects are the same class.
	 */
	public boolean equals(Object obj) { return obj instanceof Void; }
	/**
	 * Does nothing, Void is Void. Void was always final, and will always be final.
	 */
	protected void finalize() {}
	
	/**
	 * @return 0, because Void = Void;
	 */
	public int compareTo(Void v) { return 0; }
	/**
	 * @return {@link #INSTANCE}
	 */
	public Iterator<Void> iterator() { return this; }
	/**
	 * @return false, because it is just the same 'value'
	 */
	public boolean hasNext() { return false; }
	/**
	 * @return {@link #INSTANCE}
	 */
	public Void next() { return this; }
	
	public int size() { return 0; }
	public boolean isEmpty() { return true; }
	public boolean contains(Object o) { return equals(o); }
	public Object[] toArray() { return AS_ARRAY; }
	public <T> T[] toArray(T[] a) { return a; }
	public boolean add(Void e) { return false; }
	public boolean remove(Object o) { return false; }
	public boolean containsAll(Collection<?> c) { return false; }
	public boolean addAll(Collection<? extends Void> c) { return false; }
	public boolean addAll(int index, Collection<? extends Void> c) { return false; }
	public boolean removeAll(Collection<?> c) { return false; }
	public boolean retainAll(Collection<?> c) { return false; }
	public void clear() {}
	public Void get(int index) { return this; }
	public Void set(int index, Void element) { return this; } 
	public void add(int index, Void element) {}
	public Void remove(int index) { return this; }
	public int indexOf(Object o) { return 0; }
	public int lastIndexOf(Object o) { return 0; }
	public ListIterator<Void> listIterator() { return AS_LIST_ITERATOR; }
	public ListIterator<Void> listIterator(int index) { return AS_LIST_ITERATOR; }
	public List<Void> subList(int fromIndex, int toIndex) { return this; }
	
}
