package net.aether.lib.lambda;

@FunctionalInterface
public interface BiConverter<A, B, C> {
	A convert(B b, C c);
}
