package net.aether.lib.lambda;

@FunctionalInterface
public interface BiConsumer<A, B> {
	void call(A a, B b);
}
