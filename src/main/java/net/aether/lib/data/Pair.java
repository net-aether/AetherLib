package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.util.*;

import net.aether.lib.annotation.Since;

/**
 * 
 * @author Kilix
 *
 * @param <K>
 * @param <V>
 */
@Since(V0_0_1)
public class Pair<K, V> {
	
	public static <K, V> Pair<K, V> of(K key, V value) { return new Pair<>(key, value); }
	
	private K key;
	private V value;
	
	private boolean locked = false;
	
	private Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public V getValue() 		  { return value; }
	public K getKey()   		  { return key; }
	
	public void setValue(V value) { if (!locked) this.value = value; }
	public void setKey(K key) 	  { if (!locked) this.key = key; }
	
	public void    lock() 		  { locked = true; }
	public boolean isLocked() 	  { return locked; }
	
	public static <K, V> Pair<K, V> fromMap(Map<K, V> map, int index) {
		K key = new ArrayList<> (map.keySet()).get(index);
		V value = map.get(key);
		
		return new Pair<>(key, value);
	}
	
	public static <K, V> List<Pair<K, V>> convertMap(Map<K, V> map) {
		List<Pair<K, V>> out = new ArrayList<>();
		for (int i = 0; i < map.size(); i++) out.add(fromMap(map, i));
		return out;
	}
	
	@Override
	public String toString() {
		return "<" + key + " = " + value + ">";
	}
	
}
