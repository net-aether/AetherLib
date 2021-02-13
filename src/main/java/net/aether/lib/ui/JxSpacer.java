package net.aether.lib.ui;

import java.awt.Component;
import java.awt.Dimension;

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
	public Dimension getPreferredSize()  { return new Dimension(width, height); }
	
}
