package net.aether.lib.misc;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

@Since(V0_0_1)
public enum AetherLibVersion {
	V0_0_1_SNAPSHOT("net.aether:lib:0.0.1-SNAPSHOT"),
	V0_0_1         ("net.aether:lib:0.0.1");
	
	private final String prettyText;
	
	AetherLibVersion(String prettyText) {
		this.prettyText = prettyText;
	}
	
	@Override
	public String toString() {
		return prettyText;
	}
}
