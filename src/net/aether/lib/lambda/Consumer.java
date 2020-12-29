package net.aether.lib.lambda;

@FunctionalInterface
public interface Consumer<A> {
	void call(A a);
}
