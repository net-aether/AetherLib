package net.aether.lib.ui;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.awt.*;
import java.io.IOException;

import net.aether.lib.annotation.Since;

@Since(V0_0_1)
public class IncludedFonts {
	
	public static void loadAll() throws FontFormatException, IOException {
		loadAileron();
	}
	
	public static void loadAileron() throws FontFormatException, IOException {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, IncludedFonts.class.getResourceAsStream("/font/Aileron.ttf")));
	}
}
