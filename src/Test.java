import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.aether.lib.awt.GraphicUtils;

public class Test {

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setContentPane(new JPanel() {
			@Override
			public void paint(Graphics g) {
				
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
