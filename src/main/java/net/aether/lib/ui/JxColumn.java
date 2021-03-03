package net.aether.lib.ui;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JComponent;

import net.aether.lib.annotation.Since;
import net.aether.lib.data.Wrapper;
import net.aether.lib.misc.StaticLoggerUtils;

/**
 * Puts all child components horizontally next to eachother
 * @author Kilix
 */
@Since(V0_0_1)
public class JxColumn extends JComponent {
	private static final long serialVersionUID = 4736489653350579449L;

	public enum HorizontalAlignment {
		LEFT,
		CENTER,
		RIGHT
	}
	
	public JxColumn() { this(HorizontalAlignment.LEFT); }
	public JxColumn(HorizontalAlignment alignment) {
		this.alignment = alignment;
		setLayout(new JxColumnLayout());
	}
	
	private HorizontalAlignment alignment = HorizontalAlignment.LEFT;
	private List<JxSpacer> spacers = new ArrayList<>();
	
	public JxColumn addAll(Component... components) {
		for (Component c : components) {
			if (c instanceof JxSpacer) spacers.add((JxSpacer) c);
			super.add(c);
		}
		return this;
	}
	
	public JxColumn align(HorizontalAlignment alignment) {
		this.alignment = alignment;
		return this;
	}
	
	@Override
	public boolean isPreferredSizeSet() { return true; }
	
	private class JxColumnLayout implements LayoutManager {
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
				if (c.getPreferredSize().width > width) width = c.getPreferredSize().width;
				height += c.getPreferredSize().height;
			}
			
			return new Dimension(width, height);
		}
		@Override
		public void layoutContainer(Container parent) {
			final int parentHeight = parent.getHeight();
			Component[] components = parent.getComponents();
			
			Wrapper<Integer> maxWidth = Wrapper.wrap(0);
			Wrapper<Integer> heightTotal = Wrapper.wrap(0);
			
			List<JxSpacer> spacers = Arrays
					.stream(components)
					.peek(c -> {
						maxWidth.set(c.getPreferredSize().width > maxWidth.get() ? c.getPreferredSize().width : maxWidth.get());
						if (! (c instanceof JxSpacer)) heightTotal.set(heightTotal.get() + c.getPreferredSize().height);
					})
					.filter(c -> c instanceof JxSpacer)
					.map(c -> (JxSpacer) c)
					.collect(Collectors.toList());

			// This block determines the JxSpacer size
			int space = 0;
			if (heightTotal.get() < parentHeight) {
				space = parentHeight - heightTotal.get();
				if (spacers.size() != 0) space /= spacers.size();
			}
			
			for (JxSpacer spacer : spacers) {
				spacer.setWidth(maxWidth.get());
				spacer.setHeight(space);
			}
			
			// actually layout the components
			int heightSoFar = 0;
			for (Component comp : components) {
				int width = comp.getPreferredSize().width;
				int height = comp.getPreferredSize().height;
				
				switch (alignment) {
					case LEFT:
						comp.setBounds(0, heightSoFar, width, height);
						break;
					case CENTER:
						comp.setBounds((maxWidth.get() - width) / 2, heightSoFar, width, height);
						break;
					case RIGHT:
						comp.setBounds(maxWidth.get() - width, heightSoFar, width, height);
				}
				
				heightSoFar += height;
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
