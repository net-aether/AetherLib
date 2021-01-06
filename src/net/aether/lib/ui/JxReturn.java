package net.aether.lib.awt;

import java.util.Map;

public interface JxReturn {
	
	/**
	 * Gets the String to be displayed, by looking into the supplied dictionary Map.
	 * @param dict
	 * @return
	 */
	default String getDisplay(Map<String, String> dict) { return dict.containsKey(getDictName()) ? dict.get(getDictName()) : getDictName(); }
	/**
	 * Gets the String to be displayed
	 * @return
	 */
	default String getDisplay() {
		String[] path = getDictName().split(".");
		return path.length == 0 ? "NONE" : path[path.length - 1];
	}
	/**
	 * Gets the Key that the JxReturn is looking for
	 * @return
	 */
	String getDictName();
	
	public static enum Confirm implements JxReturn {
		YES("yes"),
		NO("no"),
		CANCEL("cancel");
		
		public final String translate;
		Confirm(String translate) { this.translate = translate; }
		public String getDictName() { return translate; }
	}
	public static enum YesOrNo implements JxReturn {
		YES("yes"),
		NO("no");
		
		public final String translate;
		YesOrNo(String translate) { this.translate = translate; }
		public String getDictName() { return translate; }
	}
}
