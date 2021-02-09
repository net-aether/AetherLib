package net.aether.lib.misc;

/**
 * Provides various utility functions regarding strings
 * 
 * @author Cheos, Kilix
 *
 */
public class StringUtils {
	/**
	 * Repeats a specified string by a specified amount
	 * 
	 * @param str the string to be repeated
	 * @param amount
	 * @return <b>str</b> repeated <b>amount</b> times
	 * 
	 * @author Cheos
	 * @see {@link String#repeat(int)} on JDK11+
	 */
	public static String repeat(String str, int amount) {
		String s = "";
		for (int i = 0; i < amount; i++) s += str;
		return s;
	}
}
