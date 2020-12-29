package net.aether.lib.data;

import java.util.*;

import net.aether.lib.lambda.Consumer;
import net.aether.lib.lambda.BiConsumer;

@SuppressWarnings("unchecked")
public class AetherList<T> implements List<T> {

	private T[] values;
	private Class<T> clazz;
	
	public AetherList(T... values) {
		this.values = values;
		clazz = (Class<T>) values.getClass().getComponentType();
	}
	
	public AetherList() {
		values = (T[]) new Object[0];
		clazz = (Class<T>) values.getClass().getComponentType();
	}
	
	public AetherList(List<T> values) {
		this.values = (T[]) values.toArray();
		clazz = (Class<T>) values.getClass().getComponentType();
	}
	
	
	public AetherList(Set<T> values) {
		addAll(values);
		clazz = (Class<T>) values.getClass().getComponentType();
	}
	
	/**
	 * Crushes a Map down into a List.<br>
	 * This is done by creating a {@link Pair} of every entry, and putting those into a list.<br>
	 * Accessing either the key or the value is not a fun thing to do, so this is probably most useful for easier outputs using {@link AetherList#multilineString()}
	 * @param <K>
	 * @param <V>
	 * @param map
	 * @return
	 */
	public static <K, V> AetherList<Pair<K, V>> crushMap(Map<K, V> map) {
		return new AetherList<>(Pair.convertMap(map));
	}
	
	/**
	 * Crushes a Dictionary down into a List.<br>
	 * This is done by creating a {@link Pair} of every pair, and putting those inta a list.<br>
	 * Accessing either the key or the value is not a fun thing to do, so this is probably most useful for easier outputs using {@link AetherList#multilineString()}
	 * @param <K>
	 * @param <V>
	 * @param dict
	 * @see #crushMap(Map)
	 */
	public static <K, V> AetherList<Pair<K, V>> crushDictionary(Dictionary<K, V> dict) {
		AetherList<Pair<K, V>> values = new AetherList<>();
		
		Enumeration<K> enumeration = dict.keys();
		while (enumeration.hasMoreElements()) {
			K key = enumeration.nextElement();
			values.add(new Pair<K, V>(key, dict.get(key)));
		}
		
		return values;
	}
	
	@Override
	public int size() {
		return values.length;
	}

	@Override
	public boolean isEmpty() {
		return values.length == 0;
	}

	@Override
	public boolean contains(Object o) {
		for (T t : values) if (t == o) return true;
		return false;
	}

	@Override
	public Iterator<T> iterator() {
		return new AetherIterator<T>(values);
	}

	@Override
	public T[] toArray() {
		return values;
	}
	
	public String[] toStringArray() {
		String[] out = new String[values.length];
		for (int i = 0; i < values.length; i++) out[i] = values[i].toString();
		return out;
	}

	@Override
	public Object[] toArray(Object[] a) {
        if (a.length < values.length)
            return (T[]) Arrays.copyOf(values, values.length, a.getClass());
        System.arraycopy(values, 0, a, 0, values.length);
        if (a.length > values.length)
            a[values.length] = null;
        return a;
	}

	@Override
	public boolean add(T e) {
		values = Arrays.copyOf(values, values.length + 1);
		values[values.length - 1] = (T) e;
		clazz = (Class<T>) values.getClass().getComponentType();
		return true;
	}

	@Override
	public boolean remove(Object o) {
		if (o.getClass() != clazz) return false;
		
		for (int i = 0; i < values.length; i++) {
			if (values[i] == o) {
				remove(i);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		boolean ok = true;
		for (Object o : c) if (! contains(o)) ok = false;
		return ok;
	}
	
	@SuppressWarnings("rawtypes") 
	@Override
	public boolean addAll(Collection c) {
		boolean ok = true;
		for (Object o : c) if (! add((T) o)) ok = false;
		return ok;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean addAll(int index, Collection c) {
		int i = 0;
		boolean ok = false;
		
		for (Object o : c) {
			if (o.getClass() != clazz) ok = false;
			else add(index + i, o);
			i++;
		}
		
		return ok;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		boolean ok = true;
		for (Object o : c) if (! remove(o)) ok = false; 
		return ok;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		boolean ok = true;
		Object[] temp = new Object[0];
		
		for (T t : values) {
			if (c.contains(t)) {
				temp = Arrays.copyOf(temp, temp.length + 1);
				temp[temp.length - 1] = t;
			} else ok = false;
		}
		
		return ok;
	}

	@Override
	public void clear() {
		values = (T[]) new Object[0];
	}

	@Override
	public T get(int index) {
		if (index > values.length - 1) throw new ArrayIndexOutOfBoundsException();
		return values[index];
	}

	@Override
	public T set(int index, Object element) {
		if (element.getClass() != clazz) return null;
		if (values.length > index) {
			T out = values[index];
			values[index] = (T) element;
			return out;
		} else {
			add((T) element);
			return null;	
		}
	}

	@Override
	public void add(int index, Object element) {
		if (element.getClass() != clazz) return;
		T[] newValues = Arrays.copyOf(values, values.length + 1);
		
		for (int i = 0; i < index; i++) newValues[i] = values[i]; 							// everything before the index stays the same
		newValues[index] = (T) element;														// Place the element at the index
		for (int i = index + 1; i < newValues.length; i++) newValues[i] = values[i - 1];	// add everything after the index (values[i - 1) because the new element shifts everything one back.
		values = newValues;																	// set the values to the new values
	}

	@Override
	public T remove(int index) {
		if (index >= values.length) return null;											// make sure the index is in bounds
		T out = values[index];																// store the old object
		
		T[] newValues = Arrays.copyOf(values, values.length - 1);
		for (int i = 0; i < index; i++) newValues[i] = values[i];							// everything before the index stays the same
		for (int i = index; i < newValues.length; i++) newValues[i] = values[i + 1];		// add everything after the index (values[i + 1] because the removed element shifts everything to the front
		
		values = newValues;																	// set the values to the new values
		
		return out;
	}

	@Override
	public int indexOf(Object o) {
		if (o == null || o.getClass() != clazz) return -1;
		for (int i = 0; i < values.length; i++) if (values[i] == o) return i;
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		if (o == null || o.getClass() != clazz) return -1;
		
		for (int i = values.length - 1; i >= 0; i++) if (values[i] == o) return i;
		
		return -1;
	}

	@Override
	public ListIterator<T> listIterator() {
		// TODO: ListIterator
		return null;
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		// TODO: ListIterator starting at <index>
		return null;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		if (fromIndex < 0 || toIndex >= values.length) return null;
		AetherList<T> subList = new AetherList<>();
		for (int i = fromIndex; i < toIndex; i++) subList.add(values[i]);
		return subList;
	}

	@Override
	public String toString() {
		String out = "[";
		for (int i = 0; i < values.length; i++) {
			out += values[i].toString();
			if (i + 1 < values.length) { out += ", "; }
		}
		return out + "]";
	}
	
	// Additional custom methods \\
	/**
	 * @param e the element to search for
	 * @return how many of the Element are in the List<br>
	 * returns -1 if the types do not match.
	 */
	public int count(Object e) {
		if (e.getClass() != clazz) return -1;
		int count = 0;
		for (T t : values)  if (t == e) count++;
		return count;
	}
	
	/**
	 * @param e
	 * @return an array of indices, at which the Element can be found
	 */
	public int[] occurences(Object e) {
		int[] out = new int[0];
		
		for (int i = 0; i < values.length; i++) if (values[i] == e) {
			out = Arrays.copyOf(out, out.length + 1);
			out[out.length - 1] = i;
		}
		
		return out;
	}
	
	/**
	 * Executes the runnable code for every element of the list
	 * @param runnable
	 */
	public void forEach(Consumer<T> runnable) { for (T t : values) runnable.call(t); }
	/**
	 * Executes the runnable code for every element of the list, and gives the index via the <span style="color:orange">a</span> variable
	 * @param runnable
	 */
	public void advancedForEach(BiConsumer<T, Integer> runnable) {
		int i = 0;
		for (T t : values) {
			runnable.call(t, i);
			i++;
		}
	}
	
	public static AetherList<Integer> createFromIntArray(int[] values) {
		Integer[] temp = new Integer[values.length];
		for (int i = 0; i < values.length; i++) temp[i] = Integer.valueOf(values[i]);
		return new AetherList<>(temp);
	}

	/**
	 * Adds a \n after every entry
	 * @return
	 */
	public String multilineString() {
		String out = "";
		for (int i = 0; i < values.length; i++) {
			out += values[i].toString();
			if (i + 1 < values.length) { out += "\n"; }
		}
		return out;
	}

	
}
