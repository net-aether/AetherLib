package net.aether.lib.lambda;

@FunctionalInterface
public interface TriConverter<A, B, C, D> {
	A convert(B b, C c, D d);
}
