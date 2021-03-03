package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

/**
 * Used to avoid {@link NullPointerException NullPointerExceptions}
 * @author Kilix
 *
 */
@Since(V0_0_1)
public final class Void {
	
	/**
	 * This is the OG
	 */
	public static final Void INSTANCE = new Void();
	
	private Void() {}
	
	/**
	 * @return "null", as a String!
	 */
	@Override
	public String toString() { return "null"; }
	/**
	 * @return {@link #INSTANCE itself}
	 */
	@Override
	protected Object clone() throws CloneNotSupportedException { return this; }
	/**
	 * @return true if obj is of type {@link Void}
	 */
	@Override
	public boolean equals(Object obj) { return obj instanceof Void; }
	/**
	 * @return 0
	 */
	@Override
	public int hashCode() { return 0; }
	
	/**
	 * Checks if the Object is not null, if it is, returns {@link #INSTANCE the OG}
	 * @param in
	 * @return a non-null Object, or {@link #INSTANCE the OG}
	 */
	public static Object check(Object in) {
		return in == null ? INSTANCE : in;
	}
	
}
