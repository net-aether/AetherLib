package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.util.*;

import net.aether.lib.annotation.Since;

/**
 * This class stores a list of all Keys, that point to the same Value.<br>
 * This allows for easy reverse-lookup of Keys.
 * @author Kilix
 *
 * @param <K>
 * @param <V>
 */
@Since(V0_0_1)
public class DoubleHashMap<K, V> implements Map<K, V> {

	private HashMap<K, V> map 				= new HashMap<>();
	private HashMap<V, List<K>> reverseMap 	= new HashMap<>();
	
	/**
	 * Construcs an Empty DoubleHashedMap
	 */
	public DoubleHashMap() {}
	/**
	 * Constructs a DoubleHashMap filled with the pairs of the supplied map
	 * @param initialValues
	 */
	public DoubleHashMap(Map<? extends K, ? extends V> initialValues) {
		putAll(initialValues); // Puts every single pair into the map, this needs to be done, so that the reverseMap will get filled as well
	}
	/**
	 * Constructs a DoubleHashMap filles with the supplied pairs.
	 * @param initialValues
	 */
	public DoubleHashMap(Pair<K, V>[] initialValues) {
		for (Pair<K, V> pair : initialValues) put(pair.getKey(), pair.getValue());
	}
	
	@SafeVarargs
	public static <K, V> DoubleHashMap<K, V> combine(Map<? extends K, ? extends V>... maps) {
		DoubleHashMap<K, V> out = new DoubleHashMap<>();
		
		for (Map<? extends K, ? extends V> map : maps) out.putAll(map);
		
		return out;
	}
	
	@Override
	public int size() {
		return map.size();
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return reverseMap.containsKey(value);
	}

	@Override
	public V get(Object key) {
		return map.get(key);
	}

	@Override
	public V put(K key, V value) {
		if (containsKey(key)) reverseMap.get(value).remove(key);						// If Key had previously a value, remove the key from the list of keys that whave the value
		
		if (! reverseMap.containsKey(value)) reverseMap.put(value, new ArrayList<K>()); // If the the value has not been used before, create the list to store the keys that vahe the value
		reverseMap.get(value).add(key);													// Add the key to the list of keys that have the value
		
		return map.put(key, value);														// return the priviously set value of the key
	}

	@Override
	public V remove(Object key) {
		try {
			V value = map.get(key);
			
			map.remove(key);
			reverseMap.get(value).remove(key);
			if (reverseMap.get(value).isEmpty()) reverseMap.remove(value);
			
			return value;
		} catch (NullPointerException nullExcept) {
			return null;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map) {
		for (K key : map.keySet()) put(key, map.get(key));
	}

	@Override
	public void clear() {
		map.clear();
		reverseMap.clear();
	}

	@Override
	public Set<K> keySet() {
		return map.keySet();
	}

	@Override
	public Collection<V> values() {
		return map.values();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}
	
	// Custom \\
	public List<K> getKeysWithValue(V value) {
		return reverseMap.get(value);
	}
	
	@Override
	public String toString() {
		String out = "DoubleHashedMap:{ ";
		for (K k : keySet()) out += k.toString() + "=" + get(k).toString() + " ";
		out += " }";
		return out;
	}
	
}
