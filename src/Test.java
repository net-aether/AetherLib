import javax.swing.UIManager;

public class Test {
	
	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
		main2(args);
	}
	
	@SuppressWarnings("all")
	public static void main2(String[] args) {
		System.out
		.format("%-16.3f|%-16.3f|%-16.3f\n", 69.5, 0f, 0f)
		.format("%-16s|%-16s|%-16s\n", "Hallo", "Welt", "XD")
		.format("%-16s|%-16s|%-16s\n", "69", "420", "rofl");
		
	}
	
	public static <T> void test(T... args) {
	}
	
}
