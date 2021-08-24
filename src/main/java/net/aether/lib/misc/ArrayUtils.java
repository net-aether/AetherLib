package net.aether.lib.misc;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.io.PrintStream;

import net.aether.lib.annotation.Since;

/**
 * Provides various utility functions regarding arrays
 * 
 * @author Cheos
 */
@Since(V0_0_1)
public class ArrayUtils {
	/**
	 * Prints all elements of a string array using <b>stream</b>.
	 * @see #printArray(String...) printArray(String...)
	 * @see #printArray(Object...) printArray(T...)
	 * @see #printArray(PrintStream, Object...) printArray(PrintStream, T...)
	 */
	public static void printArray(PrintStream stream, String... array) {
		if (array == null) { stream.println("null"); return; }
		int i = 0;
		stream.print("[");
		for (String str : array)
			if (str != null) stream.print((i++ != 0 ? ", " : "") + str);
		stream.println("]");
	}
	
	/**
	 * Prints all elements of a string array.
	 * @see #printArray(PrintStream, String...) printArray(PrintStream, String...)
	 * @see #printArray(Object...) printArray(T...)
	 * @see #printArray(PrintStream, Object...) printArray(PrintStream, T...)
	 */
	public static void printArray(String... array) {
		printArray(System.out, array);
	}

	/**
	 * Prints all elements of an array using <b>stream</b>.
	 * @see #printArray(String...) printArray(String...)
	 * @see #printArray(PrintStream, String...) printArray(PrintStream, String...)
	 * @see #printArray(Object...) printArray(T...)
	 * @see #toStringArray(Object...) toStringArray(T...)
	 */
	@SafeVarargs
	public static <T> void printArray(PrintStream stream, T... array) {
		printArray(stream, toStringArray(array));
	}
	
	/**
	 * Prints all elements of an array.
	 * @see #printArray(String...) printArray(String...)
	 * @see #printArray(PrintStream, String...) printArray(PrintStream, String...)
	 * @see #printArray(PrintStream, Object...) printArray(PrintStream, T...)
	 * @see #toStringArray(Object...) toStringArray(T...)
	 */
	@SafeVarargs
	public static <T> void printArray(T... array) {
		printArray(System.out, array);
	}
	
	/**
	 * @return A {@link String[]} containing all the strings produced by calling {@link Object#toString()} on each element of <b>array</b>
	 */
	@SafeVarargs
	public static <T> String[] toStringArray(T... array) {
		if (array == null) return null;
		String[] out = new String[array.length];
		for (int i = 0; i < array.length; i++)
			out[i] = array[i] == null ? "null" : array[i].toString();
		return out;
	}
	
	/**
	 * TODO: @kilix document this
	 * @param arrays
	 * @return
	 */
	public static String[] mergeStrings(String[]... arrays) {
		return mergeStrings(true, arrays);
	}
	
	/**
	 * TODO: @kilix document this
	 * @param pad
	 * @param arrays
	 * @return
	 */
	public static String[] mergeStrings(boolean pad, String[]... arrays) {
		return mergeStrings(pad, ' ', arrays);
	}
	
	/**
	 * TODO: @kilix document this
	 * @param padding
	 * @param arrays
	 * @return
	 */
	public static String[] mergeStrings(char padding, String[]... arrays) {
		return mergeStrings(true, padding, arrays);
	}
	
	/**
	 * TODO: @kilix document this
	 * @param pad
	 * @param padding
	 * @param arrays
	 * @return
	 */
	public static String[] mergeStrings(boolean pad, char padding, String[]... arrays) {
		return mergeStrings(pad, padding, 1, arrays);
	}
	
	/**
	 * TODO: @kilix document this
	 * Note: arrays[any] may not be null, arrays[any][any] may be null
	 * @param pad
	 * @param padding
	 * @param spacing
	 * @param arrays
	 * @return
	 * @author Cheos (method)
	 * @author Kilix (documentation)
	 */
	public static String[] mergeStrings(boolean pad, char padding, int spacing, String[]... arrays) {
		if (arrays == null)     return null;
		if (arrays.length == 0) return new String[0];
		if (arrays.length == 1) return arrays[0];
		
		int maxlen = 0;
		for (String[] arr : arrays)
			if (arr.length > maxlen) maxlen = arr.length;
		
		int[] strmax = new int[arrays.length];
		if (pad)
			for (int i = 0; i < arrays.length; i++)
				for (String s : arrays[i]) {
					if (s == null) s = "null";
					if (strmax[i] < s.length())
						strmax[i] = s.length() + spacing;
				}
		
		String[] out = new String[maxlen];
		
		for (int i = 0; i < maxlen; i++)
			if (arrays[0].length > i)
				out[i] = (arrays[0][i] == null ? "null" : arrays[0][i])
						+ StringUtils.repeat("" + padding, strmax[0] - (arrays[0][i] == null ? 4 : arrays[0][i].length()));
			else out[i] = StringUtils.repeat("" + padding, strmax[0]);
		
		for (int j = 1; j < arrays.length; j++)
			for (int i = 0; i < maxlen; i++)
				if (arrays[j].length > i)
					out[i] += (arrays[j][i] == null ? "null" : arrays[j][i])
							+ (j == arrays.length - 1
							? ""
							:  StringUtils.repeat("" + padding, strmax[j] - (arrays[j][i] == null ? 4 : arrays[j][i].length())));
				else if (j != arrays.length - 1) out[i] += StringUtils.repeat("" + padding, strmax[j]);
		
		return out;
	}
}
