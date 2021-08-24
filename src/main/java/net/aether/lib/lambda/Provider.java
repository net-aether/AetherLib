package net.aether.lib.lambda;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

@FunctionalInterface
@Since(V0_0_1)
public interface Provider<A> {
	A get();
}
