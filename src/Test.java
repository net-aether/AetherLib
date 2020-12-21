import java.awt.*;

import javax.swing.*;

import net.aether.lib.awt.GraphicUtils;

public class Test {

	public static void main(String[] args) {
		try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); } catch (Exception e) {}
		UIManager.getLookAndFeel().getDefaults().forEach((k, v) -> System.out.println(k + "=" + v));
	}
	
	@SuppressWarnings("all")
	public static void main2(String[] args) {
		JFrame frame = new JFrame();
		
		frame.setUndecorated(true);
		frame.setBackground(new Color(1f, 1f, 1f, 0.5f));
		
		frame.setBounds(new Rectangle(0, 0, 1028, 720));
		
		frame.setContentPane(new JPanel() {
			@Override
			public Color getBackground() {
				return new Color(0f, 0f, 0f, 0f);
			}
			
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				
				boolean color = false;
				for (int i = 0; i < getHeight(); i+= 10) {
					g.setColor(
							(color = !color) ?
								Color.red:
								Color.yellow
							); 
					g.fillRect(0, i, 10, 10);
				}
				
				g.setColor(Color.BLACK);
				g.setFont(g.getFont().deriveFont(70f));
				GraphicUtils.drawCenteredString(g, getBounds(), "Hello World");

			}
		});
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		new Thread(() -> {
			while (true) {
				frame.repaint();
				try { Thread.sleep(100); } catch (Exception e) {}
			}
		}).start();
	}
	
}
