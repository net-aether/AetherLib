package net.aether.lib.swing;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import javax.swing.*;

/**
 * Adds a TitleBar to a component or Frame.<br>
 * Allows to add Buttons, an Icon and a Title.<br>
 * Draggability can be set or toggled.
 * 
 * @author Kilix
 */
public class TitleBar extends JComponent {

	private static final long serialVersionUID = -6452905165526316417L;

	// Parent \\
	private JFrame parentFrame;
	private JComponent parentComponent;
	private boolean frameParent;
	
	private String title;
	private Icon icon;
	private List<JComponent> children;
	private boolean useLookAndFeel;
	private boolean draggable;
	private Color backgroundColor;
	
	public TitleBar(JFrame parent) 	    { this.parentFrame 	   = parent; }
	public TitleBar(JComponent parent)  { this.parentComponent = parent; }
	
	public boolean hasFrameParent() 	{ return  frameParent; }
	public boolean hasComponentParent() { return !frameParent; }
	
	/**
	 * Sets the title of the TitleBar and returns itself
	 * @param title
	 * @return
	 */
	public TitleBar setTitle(String title) {
		this.title = title;
		return this;
	}

	public TitleBar setIcon(Icon newIcon) {
		return this;
	}
	
	public TitleBar setDraggable(boolean draggable) {
		return this;
	}
	
}
