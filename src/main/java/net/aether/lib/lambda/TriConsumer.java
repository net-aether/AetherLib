package net.aether.lib.lambda;

@FunctionalInterface
public interface TriConsumer<A, B, C> {
	void call(A a, B b, C c);
}
