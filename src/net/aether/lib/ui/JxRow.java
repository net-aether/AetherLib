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
	
	public enum VerticalAlignment {
		TOP,
		MIDDLE,
		BOTTOM
	}
	
	public JxRow() {
		setLayout(new JxRowLayout());
	}
	
	private VerticalAlignment alignment = VerticalAlignment.BOTTOM;
	private List<JxSpacer> spacers = new ArrayList<>();
	
	public JxRow addAll(Component... components) {
		for (Component c : components) {
			if (c instanceof JxSpacer) spacers.add((JxSpacer) c);
			super.add(c);
		}
		return this;
	}
	
	private class JxRowLayout implements LayoutManager {
		public void addLayoutComponent(String name, Component comp) {}
		public void removeLayoutComponent(Component comp) {}
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
		public Dimension minimumLayoutSize(Container parent) {
			System.out.format("minimumLayoutSize(%s)\n", parent.toString());
			return new Dimension(0, 0);
		}
		public void layoutContainer(Container parent) {
			final int parentWidth = parent.getWidth();
			Component[] components = parent.getComponents();
			Wrapper<Integer> maxHeight = Wrapper.wrap(0);
			
			List<JxSpacer> spacers = Arrays
					.stream(components)
					// Determines the height for the row
					.peek(c -> maxHeight.set(c.getHeight() > maxHeight.get() ? c.getHeight() : maxHeight.get()))
					// Filters for JxSpacers
					.filter(c -> c instanceof JxSpacer)
					// Parses the stream
					.map(c -> (JxSpacer) c)
					// Collects the stream back to a map
					.collect(Collectors.toList());

			int widthTotal = 0; // total width so far
			for (Component comp : components ) {
				int width = comp.getPreferredSize().width;
				int height = comp.getPreferredSize().height;
				
				switch (alignment) {
					case TOP:
						comp.setBounds(widthTotal, 0, width, height);
						break;
					case BOTTOM:
						comp.setBounds(widthTotal, maxHeight.get() - height, width, height);
						break;
					case MIDDLE:
						comp.setBounds(widthTotal, maxHeight.get() - (height / 2), width, height);
				}
				
				if (! (comp instanceof JxSpacer)) widthTotal += width;
			}
			
			StaticLoggerUtils.sysout(String.valueOf(maxHeight) + "x" + String.valueOf(widthTotal));
			
			int space = 0;
			if (widthTotal < parentWidth) {
				space = parentWidth - widthTotal;
				space /= spacers.size();
			}
			for (JxSpacer spacer : spacers) spacer.setWidth(space);
		}
		
	}
	
}
