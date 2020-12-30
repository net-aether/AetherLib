package net.aether.lib.data;

/**
 * Used to avoid {@link NullPointerException}
 * @author Kilix
 *
 */
public final class Void {
	
	/**
	 * This is the OG
	 */
	public static final Void INSTANCE = new Void();
	
	private Void() {}
	
	/**
	 * @return "null", as a String!
	 */
	public String toString() { return "null"; }
	/**
	 * @return {@link #INSTANCE itself}
	 */
	protected Object clone() throws CloneNotSupportedException { return this; }
	/**
	 * @return true if obj is of type {@link Void}
	 */
	public boolean equals(Object obj) { return obj instanceof Void; }
	/**
	 * @return 0
	 */
	public int hashCode() { return 0; }
	
	/**
	 * Checks if the Object is not null, if it is, returns {@link #INSTANCE the OG}
	 * @param in
	 * @return a non-null Object, or {@link #INSTANCE the OG}
	 */
	public static Object check(Object in) {
		return in == null ? new Void() : in;
	}
	
}
