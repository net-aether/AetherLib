package net.aether.lib.io;

import java.io.*;

import net.aether.lib.data.AetherList;

public class FileContentReader {

	/**
	 * Reads the contents of the File into a String Array<br>
	 * If there was an error, an empy array will be returned.
	 * @param file
	 * @return
	 */
	public static String[] readFileContentsAsArray(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			AetherList<String> out = new AetherList<>();
			while (reader.ready()) out.add(reader.readLine());		
			return out.toStringArray();
		} catch (Exception e) {
			return new String[0];
		}
	}
	
	public static String readFileContents(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String out = "";
			while (reader.ready()) out += reader.readLine() + "\n";
			return out;
		} catch (Exception e) {
			return "";
		}
	}
}
