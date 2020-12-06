package net.aether.lib.swing;

import javax.swing.JComponent;

public class DraggableArea extends JComponent {

	private static final long serialVersionUID = -7158961962753957424L;
	private JComponent parent;
	
	public DraggableArea(JComponent parent) {
		setParent(parent);
	}
	
	public void setParent(JComponent parent) {
		if (parent == null) throw new NullPointerException("parent of draggable area may not be null!");
		this.parent.remove(this);
		this.parent = parent;
		parent.add(this);
	}
	
}
