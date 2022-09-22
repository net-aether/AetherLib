package net.aether.lib.data;

public final class SimpleWrapper<T> {

	public SimpleWrapper() {}
	public SimpleWrapper(T value) { this.value = value; }
	
	public T value;
	
	public boolean has() { return value != null; }
	public boolean empty() { return value == null; }
	public T getOr(T other) { return value != null ? value : other; }
	public T get() { return value; }
	public <E extends Throwable> T orThrow(E throwable) throws E {
		if (has()) return value; throw throwable;
	}
}
