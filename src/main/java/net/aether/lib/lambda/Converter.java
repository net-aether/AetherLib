package net.aether.lib.lambda;

@FunctionalInterface
public interface Converter<A, B> {
	A convert(B b);
}
