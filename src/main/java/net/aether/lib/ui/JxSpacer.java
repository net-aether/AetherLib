package net.aether.lib.ui;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.awt.Component;
import java.awt.Dimension;

import net.aether.lib.annotation.Since;

/**
 * All Spacers inside the same {@link JxRow Row} will have the same width.
 * @author Kilix
 */
@Since(V0_0_1)
public class JxSpacer extends Component {
	
	private static final long serialVersionUID = 8076581856835207530L;
	private int width, height;
	
	protected void setWidth(int width) 	 { this.width = width; }
	protected void setHeight(int height) { this.height = height; }
	
	@Override
	public int getWidth() 				 { return width; }
	@Override
	public int getHeight() 				 { return height; }
	@Override
	public Dimension getPreferredSize()  { return new Dimension(width, height); }
	
}
