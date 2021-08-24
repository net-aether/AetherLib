package net.aether.lib.ui;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.awt.*;

import javax.swing.UIManager;

import net.aether.lib.annotation.Since;

@Since(V0_0_1)
public class GraphicUtils {

	/**
	 * Draws the supplied String centered within the supplied rectangle, using the supplied Graphics
	 */
	public static void drawCenteredString(Graphics g, Rectangle rect, String string) {
	    // Get the FontMetrics
	    FontMetrics metrics = g.getFontMetrics();
	    // Determine the X coordinate for the text
	    int x = rect.x + (rect.width - metrics.stringWidth(string)) / 2;
	    // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
	    int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
	    // Draw the String
	    g.drawString(string, x, y);

	}
	
	/**
	 * Simply tells the UIManager to use the system look and feel.<br>
	 * Note: UIManager is static, and the LookAndFeel will apply to all JFrames
	 */
	public static void useSystemLookAndFeel() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) { /* most exceptions should not occur on the system LAF */ }
	}
}
