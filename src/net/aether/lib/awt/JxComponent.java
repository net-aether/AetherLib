package net.aether.lib.awt;

import java.awt.Dimension;

/**
 * Identifies JxComponents
 * 
 * @author Kilix
 *
 */
public interface JxComponent {
	
	public Dimension getMinimumSize();
	public Dimension getPreferredSize();
	public Dimension getSize();
	
}
