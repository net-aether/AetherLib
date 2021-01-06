package net.aether.lib.ui;

import java.awt.*;

public class GraphicUtils {

	/**
	 * Draws the supplied String centered within the supplied rectangle, using the supplied Graphics
	 * @param g
	 * @param rect
	 * @param string
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
	
}
