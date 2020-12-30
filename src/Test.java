import javax.swing.UIManager;

import net.aether.lib.awt.JxStyle;

public class Test {
	
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
		main2(args);
	}
	
	@SuppressWarnings("all")
	public static void main2(String[] args) {
		JxStyle style = new JxStyle();
		
		System.out.println(style.colors.background);
		System.out.println(style.margin.top);
		System.out.println(style.padding.top);
	}
	
}
