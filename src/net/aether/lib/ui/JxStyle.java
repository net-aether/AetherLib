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
	
	public static class Colors {
		public Color background = Color.white;
		public Color foreground = Color.black;
	}
	
	public static class Spaces {
		private int top 		= 0;
		private int right 		= 0;
		private int bottom 		= 0;
		private int left 		= 0;
		
		/**
		 * Sets the value for all 4 values
		 * @return itself
		 */
		public Spaces all(int all) {
			top = all;
			right = all;
			bottom = all;
			left = all;
			return this;
		}
		/**
		 * Sets the value for top and bottom
		 * @return itself
		 */
		public Spaces vertical(int vertical) {
			top = vertical;
			bottom = vertical;
			return this;
		}
		/**
		 * Sets the value for left and right
		 * @return itself
		 */
		public Spaces horizontal(int horizontal) {
			right = horizontal;
			left = horizontal;
			return this;
		}
		/**
		 * Sets the value for top
		 * @return itself
		 */
		public Spaces top(int top) {
			this.top = top;
			return this;
		}
		/**
		 * Sets the value for right
		 * @return itself
		 */
		public Spaces right(int right) {
			this.right = right;
			return this;
		}
		/**
		 * Sets the value for bottom
		 * @return itself
		 */
		public Spaces bottom(int bottom) {
			this.bottom = bottom;
			return this;
		}
		/**
		 * Sets the value for left
		 * @return itself
		 */
		public Spaces left(int left) {
			this.left = left;
			return this;
		}
		
		/**
		 * @return the value for top
		 */
		public int top() 	{ return top; }
		/**
		 * @return the value for right
		 */
		public int right() 	{ return right; }
		/**
		 * @return the value for bottom
		 */
		public int bottom() { return bottom; }
		/**
		 * @return the value for left
		 */
		public int left() 	{ return left; }
		
	}
}
