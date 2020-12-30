package net.aether.lib.awt;

import javax.swing.JFrame;

import net.aether.lib.data.Promise;

/**
 * 
 * 
 * @author Kilix
 */
public class JxFrame {
	
	private final JFrame frame;
	
	public JxFrame(String title) {
		frame = new JFrame(title);
	}
	
	public Promise<JxReturn.Confirm> showConfirmDialog() { return null; }
	public Promise<JxReturn.YesOrNo> showYesOrNoDialog() { return null; }
	public Promise<JxReturn> showDialog(String title, JxComponent content, JxReturn... options) { return null; }
	
}
