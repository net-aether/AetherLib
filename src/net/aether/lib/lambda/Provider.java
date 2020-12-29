package net.aether.lib.lambda;

@FunctionalInterface
public interface Provider<A> {
	A get();
}
