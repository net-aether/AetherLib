import java.awt.Color;
import java.awt.Graphics;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

import net.aether.lib.data.Queue;
import net.aether.lib.data.SimpleQueue;
import net.aether.lib.debug.DebugTimer;
import net.aether.lib.misc.SimpleTimer;

public class Test {
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		kilixMain(args);
	}
		
	public static void kilixMain(String[] args) {
		SimpleTimer timer = new SimpleTimer();
		
		JFrame frame = new JFrame();
		
		frame.setContentPane(new JPanel() {
			public void paint(Graphics graphics) {
				
				float r = (float) (Math.sin(timer.peek() / 500f + (Math.PI / 3) * 0) + 1) / 2f;
				float g = (float) (Math.sin(timer.peek() / 500f + (Math.PI / 3) * 1) + 1) / 2f;
				float b = (float) (Math.sin(timer.peek() / 500f + (Math.PI / 3) * 2) + 1) / 2f;
				
				frame.setTitle(String.format("r:%.2f g:%.2f b:%.2f", r, g, b));
				
				graphics.setColor(new Color(r, g, b));
				graphics.fillRect(0, 0, getWidth(), getHeight());
			}
		});
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(3);
		frame.setSize(1280, 720);
				
		new Thread(() -> {
			while (true) {
				frame.repaint();
				try { Thread.sleep(10); } catch (Exception e) {}
			}
		}).start();
		
	}
	
	public static void cheosMain(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
		
		DebugTimer dt = new DebugTimer(50);
		
		Map<String, String> map = new HashMap<>();
		Random rnjesus = new Random();
		
		Queue<Integer> q = new SimpleQueue<>();

		dt.start();
		
		for (int i = 0; i < 20; i++) {
			q.push(i);
			dt.intermediate();
		}
		System.out.println(dt.getIntermediateAmount());
		dt.intermediate();
		q.get(0);
		dt.intermediate();
		q.get(10);
		dt.intermediate();
		System.out.println(dt.getIntermediateAmount());
		dt.intermediate();
		q.insert(101, 0);
		dt.intermediate();
		q.insert(101, 10);
		dt.intermediate();
		System.out.println(dt.getIntermediateAmount());
		dt.intermediate();
		
		for (int i = 0; i < 20; i++) {
			q.pop();
			dt.intermediate();
		}
		
		dt.end();
		
		
		System.out.println();
//		System.out.printf("Took %.3fms\n", dt.getMicroDiff() / 1000D);
		for (int i = 0; i < 20; i++)
			System.out.printf("Push%d segment took %.3fus\n", i, dt.getMicroDiffD(i, i + 1));
		System.out.printf("Get %d segment took %.3fus\n", 1, dt.getMicroDiffD(21, 22));
		System.out.printf("Get %d segment took %.3fus\n", 2, dt.getMicroDiffD(22, 23));
		System.out.printf("Ins %d segment took %.3fus\n", 1, dt.getMicroDiffD(24, 25));
		System.out.printf("Ins %d segment took %.3fus\n", 2, dt.getMicroDiffD(25, 26));
		for (int i = 27; i < 59; i++)
			System.out.printf("Pop %d segment took %.3fus\n", i - 27, dt.getMicroDiffD(i, i + 1));
//		System.out.println(df.format(new Date(dt.getStartMillis())));
//		System.out.println(df.format(new Date(dt.getIntermediateMillis(0))));
//		System.out.println(df.format(new Date(dt.getEndMillis())));
	}
}
