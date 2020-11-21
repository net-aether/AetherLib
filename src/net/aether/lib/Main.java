package net.aether.lib;

import java.util.Arrays;

import javax.swing.*;

import net.aether.lib.swing.TopLeftLayout;
import net.aether.lib.swing.laf.AetherLookAndFeel;

public class Main {

	public static void main(String[] args) {
		
		
		System.out.println(Arrays.asList(UIManager.getDefaults()));
		
	}
	
	public static void testFrame() {
		try { UIManager.setLookAndFeel(AetherLookAndFeel.CLASSNAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JFrame frame = new JFrame("Swing Test");
		
		frame.add(new JButton("lorem"));
		frame.add(new JButton("ipsum"));
		frame.add(new JButton("dolor"));
		frame.add(new JButton("sit"));
		frame.add(new JButton("amet"));
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new TopLeftLayout(5, 5));
		frame.setVisible(true);
		
		frame.setSize(50, 50);
		frame.pack();
	}
}
