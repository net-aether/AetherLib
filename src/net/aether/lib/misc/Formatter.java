package net.aether.lib.misc;

import java.util.Map;

import net.aether.lib.data.AetherList;
import net.aether.lib.data.Wrapper;

public class Formatter {
	
	public static String[] generateTable(Map<?, ?> values, String keyName, String valueName) {
		return generateTable(values, keyName, valueName, true);
	}

	/**
	 * Generates a Table from values of a map.<br>
	 * NOTE: equal spacing only works with monospace-fonts<br>
	 * The generated table will look like this:<br>
	 * <p style="font-family: monospace; white-space:pre;">
	 * <br>
	 * | keyName   | valueName   |
	 * |:---------:|:-----------:|
	 * | key1      | val1        |
	 * | key2      | val2        |
	 * | longerKey | val3        |
	 * | key4      | longerValue |
	 * </p>
	 * @param values a list of objects (on which .toString() is called) to be displayed
	 * @param keyName the string to be displayed above the left column
	 * @param valueName the string to be displayed above the right column
	 * @param leaveOpen if <b>true</b> the "|" on the left and right will not be added
	 * @return the {@link String[] String Array}
	 */
	public static String[] generateTable(Map<?, ?> values, String keyName, String valueName, boolean leaveOpen) {
		if (  keyName == null)   keyName = "";
		if (valueName == null) valueName = "";
		
		// Size of values + 2 because of name and line
		String[] out = new String[values.size() + 2];
		
		AetherList<String>
			keyList   = new AetherList<>(),
			valueList = new AetherList<>();
		Wrapper<Integer>
			keyLength 	= Wrapper.wrap(  keyName.length()),
			valueLength = Wrapper.wrap(valueName.length());
		
		AetherList.crushMap(values).stream().forEach(pair -> {
			String key   = pair.getKey()  .toString();
			String value = pair.getValue().toString();
						
			if (  key.length() > keyLength  .get()) keyLength  .set(key  .length());
			if (value.length() > valueLength.get()) valueLength.set(value.length());
			
			keyList.add("" + key);
			valueList.add("" + value);
		});
		
		if (leaveOpen) {
			out[0] = String.format("%-" + keyLength.get() + "s | %-" + valueLength.get() + "s", keyName, valueName);
			out[1] = "-".repeat(keyLength.get()) + ":|:" + "-".repeat(valueLength.get());
		} else {
			out[0] = String.format("| %-" + keyLength.get() + "s | %-" + valueLength.get() + "s |", keyName, valueName);
			out[1] = "|:" + "-".repeat(keyLength.get()) + ":|:" + "-".repeat(valueLength.get()) + ":|";
		}
		
		for (int i = 0; i < out.length - 2; i++) {
			if (leaveOpen)
				out[i + 2] = String.format("%-" + keyLength.get() + "s | %-" + valueLength.get() + "s", keyList.get(i), valueList.get(i));
			else
				out[i + 2] = String.format("| %-" + keyLength.get() + "s | %-" + valueLength.get() + "s |", keyList.get(i), valueList.get(i));
			
		}
		
		return out;
	}
	
}
