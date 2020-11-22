package net.aether.lib.data;

import java.util.*;

public class Pair<K, V> {

	private K key;
	private V value;
	
	private boolean unlocked = true;
	
	public Pair(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public V getValue() {
		return value;
	}
	
	public K getKey() {
		return key;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public void lock() {
		unlocked = false;
	}
	
	public boolean isLocked() {
		return ! unlocked;
	}
	
	public static <K, V> Pair<K, V> fromMap(Map<K, V> map, int index) {
		K key = new ArrayList<K> (map.keySet()).get(index);
		V value = map.get(key);
		
		return new Pair<K, V>(key, value);
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
