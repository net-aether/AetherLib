package net.aether.lib.exceptions;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

@Since(V0_0_1)
public class InvalidTypeException extends Exception {
	private static final long serialVersionUID = -6133062429890057895L;

	public InvalidTypeException(String message) { super(message); }
}
