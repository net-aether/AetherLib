package net.aether.lib.lambda;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

@FunctionalInterface
@Since(V0_0_1)
public interface Consumer<A> {
	
	/**
	 * A consumer that accepts anything, and does nothing with it.<br>
	 * Just like throwing all your precious Objects into the void
	 */
	Consumer<Object> VOID_CONSUMER = (object) -> {};
	
	void call(A a);
	
	/**
	 * @return a consumer that accepts anything and ignores calls with un-castable objects
	 */
	@SuppressWarnings("unchecked")
	default Consumer<Object> generic() {
		return ((obj) -> {
			try {
				call((A) obj);
			} catch (Exception e) {}
		});
	}
	
}
