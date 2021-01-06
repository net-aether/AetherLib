import javax.swing.JFrame;
import javax.swing.JLabel;

import net.aether.lib.ui.*;

public class Test {
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		GraphicUtils.useSystemLookAndFeel();
		JFrame frame = new JFrame("TestFrame");
		
		frame.add(new JxRow().addAll(new JxSpacer(), new JLabel("Hallo"), new JxSpacer()));
		
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		
		frame.pack();
	}
	
	
	
}
