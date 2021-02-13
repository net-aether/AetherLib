package net.aether.lib.ui;

import java.awt.*;
import java.io.IOException;

public class IncludedFonts {
	
	public static void loadAll() throws FontFormatException, IOException {
		loadAileron();
	}
	
	public static void loadAileron() throws FontFormatException, IOException {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, IncludedFonts.class.getResourceAsStream("/font/Aileron.ttf")));
	}
}
