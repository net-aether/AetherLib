package net.aether.lib.data;

public final class SimpleWrapper<T> {

	public SimpleWrapper() {}
	public SimpleWrapper(T value) { this.value = value; }
	
	public T value;
	
	public boolean has() { return value != null; }
	public boolean empty() { return value == null; }
	public T getOr(T other) { return value != null ? value : other; }
	
}
