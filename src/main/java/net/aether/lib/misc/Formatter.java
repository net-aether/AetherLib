package net.aether.lib.misc;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.util.Map;

import net.aether.lib.annotation.Since;
import net.aether.lib.data.AetherList;
import net.aether.lib.data.Pair;

@Since(V0_0_1)
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
		
		int keyLength 	=   keyName.length(),
			valueLength = valueName.length();
		
		for (Pair<?, ?> pair : AetherList.crushMap(values)) {
			String key   = pair.getKey()  .toString();
			String value = pair.getValue().toString();
						
			if (  key.length() >   keyLength)   keyLength =   key.length();
			if (value.length() > valueLength) valueLength = value.length();
			
			  keyList.add("" + key  );
			valueList.add("" + value);
		}
		
		if (leaveOpen) {
			out[0] = String     .format("%-" + keyLength + "s | %-" + valueLength + "s", keyName, valueName);
			out[1] = StringUtils.repeat( "-" , keyLength) + ":|:"   + StringUtils.repeat("-", valueLength);
		} else {
			out[0] =        String     .format("| %-" + keyLength + "s | %-" + valueLength + "s |", keyName, valueName);
			out[1] = "|:" + StringUtils.repeat("-"    , keyLength) + ":|:"   + StringUtils.repeat("-", valueLength) + ":|";
		}
		
		for (int i = 0; i < out.length - 2; i++)
			if (leaveOpen)
				out[i + 2] = String.format( "%-"  + keyLength + "s | %-" + valueLength + "s"  , keyList.get(i), valueList.get(i));
			else
				out[i + 2] = String.format("| %-" + keyLength + "s | %-" + valueLength + "s |", keyList.get(i), valueList.get(i));
		return out;
	}
	
}
