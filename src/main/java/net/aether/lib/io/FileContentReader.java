package net.aether.lib.io;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.io.*;

import net.aether.lib.annotation.Since;
import net.aether.lib.data.AetherList;

@Since(V0_0_1)
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
			reader.close();
			return out.toStringArray();
		} catch (Exception e) {
			return new String[0];
		}
	}
	
	public static String readFileContents(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String out = "";
			while (reader.ready()) out += reader.readLine() + (reader.ready() ? "\n" : "");
			reader.close();
			return out;
		} catch (Exception e) {
			return "";
		}
	}
}
