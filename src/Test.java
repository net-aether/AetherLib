import javax.swing.JButton;
import javax.swing.JFrame;

import net.aether.lib.ui.*;

public class Test {
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		GraphicUtils.useSystemLookAndFeel();
		JFrame frame = new JFrame("TestFrame");
		
		/*frame.add(
				new JxRow()
					.addAll(
						new JxColumn(HorizontalAlignment.CENTER)
							.addAll(
								new JxSpacer(),
								new JxRow(VerticalAlignment.CENTER)
									.addAll(
										new JButton("Hallo"),
										new JButton("Welt")
									),
								new JxSpacer(),
								new JButton("BUTTON"),
								new JxSpacer()
							)
				)
		);*/
		
		frame.add(
				new JxColumn()
					.addAll(
						new JxSpacer(),
						new JxRow()
							.addAll(
									new JxSpacer(),
									new JButton("Hello"),
									new JButton("World"),
									new JxSpacer()
							),
						new JxSpacer()
					)
				);
		
		frame.setDefaultCloseOperation(3);
		frame.setVisible(true);
		
		frame.pack();
	}
	
	
	
}
