package net.aether.lib.ui;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import net.aether.lib.data.Wrapper;
import net.aether.lib.misc.StaticLoggerUtils;

/**
 * Puts all child components horizontally next to eachother
 * @author Kilix
 */
public class JxRow extends JComponent {
	private static final long serialVersionUID = 2289290403401405978L;

	public enum VerticalAlignment {
		TOP,
		MIDDLE,
		BOTTOM;
		
		public static final VerticalAlignment CENTER = MIDDLE;
	}
	
	public JxRow() { this(VerticalAlignment.BOTTOM); }
	
	public JxRow(VerticalAlignment alignment) {
		this.alignment = alignment;
		setLayout(new JxRowLayout());
	}
	
	private VerticalAlignment alignment;
	private List<JxSpacer> spacers = new ArrayList<>();
		
	public JxRow addAll(Component... components) {
		for (Component c : components) {
			if (c instanceof JxSpacer) spacers.add((JxSpacer) c);
			super.add(c);
		}
		return this;
	}
	
	public JxRow align(VerticalAlignment alignment) {
		this.alignment = alignment;
		return this;
	}
	
	@Override
	public boolean isPreferredSizeSet() { return true; }
	
	private class JxRowLayout implements LayoutManager {
		@Override
		public void addLayoutComponent(String name, Component comp) {}
		@Override
		public void removeLayoutComponent(Component comp) {}
		@Override
		public Dimension minimumLayoutSize(Container parent) { return preferredLayoutSize(parent); }
		@Override
		public Dimension preferredLayoutSize(Container parent) {
			int width = 0;
			int height = 0;
			
			for (Component c : parent.getComponents()) {
				if (c instanceof JxSpacer) continue;
				if (c.getPreferredSize().height > height) height = c.getPreferredSize().height;
				width += c.getPreferredSize().width;
			}
			
			return new Dimension(width, height);
		}
		@Override
		public void layoutContainer(Container parent) {
			final int parentWidth = parent.getWidth();
			Component[] components = parent.getComponents();
			
			Wrapper<Integer> maxHeight = Wrapper.wrap(0);
			Wrapper<Integer> widthTotal = Wrapper.wrap(0);
			
			List<JxSpacer> spacers = Arrays
					.stream(components)
					// Determines the height for the row
					// Determines the total width of the non Spacer components
					.map(c -> {
						System.out.println("old total: " + widthTotal);
						maxHeight.set(c.getPreferredSize().height > maxHeight.get() ? c.getPreferredSize().height : maxHeight.get());
						if (! (c instanceof JxSpacer)) widthTotal.set(widthTotal.get() + c.getPreferredSize().width);
						System.out.println("new total: " + widthTotal);
						return c;
					})
					// Filters for JxSpacers
					.filter(c -> c instanceof JxSpacer)
					// Parses the stream
					.map(c -> (JxSpacer) c)
					// Collects the stream back to a map
					.collect(Collectors.toList());

			// This block determines the JxSpacer size
			int space = 0;
			if (widthTotal.get() < parentWidth) {
				space = parentWidth - widthTotal.get();
				if (spacers.size() != 0) space /= spacers.size();
			}
			
			for (JxSpacer spacer : spacers) {
				spacer.setWidth(space);
				spacer.setHeight(maxHeight.get());
			}
			
			
			int widthSoFar = 0;
			for (Component comp : components) {
				int width = comp.getPreferredSize().width;
				int height = comp.getPreferredSize().height;
				
				switch (alignment) {
					case TOP:
						comp.setBounds(widthSoFar, 0, width, height);
						break;
					case BOTTOM:
						comp.setBounds(widthSoFar, maxHeight.get() - height, width, height);
						break;
					case MIDDLE:
						comp.setBounds(widthSoFar, (maxHeight.get() - height) / 2, width, height);
				}
				
				widthSoFar += width;
			}
		}
	}
	
	@Override
	public void repaint() {
		StaticLoggerUtils.sysout("repaint " + hashCode());
		super.repaint();
		for (Component c : getComponents()) {
			StaticLoggerUtils.sysout("repainting: " + c.getClass().getSimpleName() + "#" + c.hashCode());
			c.repaint();
		}
	}
	
}
