package net.aether.lib.ui;

import java.awt.*;

/**
 * All Spacers inside the same {@link JxRow Row} will have the same width.
 * @author Kilix
 */
public class JxSpacer extends Component {
	
	private static final long serialVersionUID = 8076581856835207530L;
	private int width, height;
	
	protected void setWidth(int width) 	 { this.width = width; }
	protected void setHeight(int height) { this.height = height; }
	
	public int getWidth() 				 { return width; }
	public int getHeight() 				 { return height; }

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.yellow);
		g.fillRect(0, 0, width, height);
	}
	
}
