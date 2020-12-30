package net.aether.lib.awt;

import java.awt.Color;

/**
 * 
 * 
 * @author Kilix
 *
 */
public class JxStyle {
	
	public Spaces margin 		= new Spaces();
	public Spaces padding 		= new Spaces();
	public Colors colors 		= new Colors();
	
	public class Colors {
		public Color background = Color.white;
		public Color foreground = Color.black;
	}
	
	public class Spaces {
		public int top 			= 0;
		public int right 		= 0;
		public int bottom 		= 0;
		public int left 		= 0;
		
		public Spaces all(int all) {
			top = all;
			right = all;
			bottom = all;
			left = all;
			return this;
		}
		
		public Spaces vertical(int vertical) {
			top = vertical;
			bottom = vertical;
			return this;
		}
		
		public Spaces horizontal(int horizontal) {
			right = horizontal;
			left = horizontal;
			return this;
		}
		
	}
}
