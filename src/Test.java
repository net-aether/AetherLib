import java.util.*;

import net.aether.lib.misc.Formatter;

public class Test {
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		
		Map<String, String> map = new HashMap<>();
		Random rnjesus = new Random();
		
		for (int i = 0; i < 10; i++) map.put("" + rnjesus.nextInt(0xFF), "" + rnjesus.nextInt(0xFF));
		
		for (String s : Formatter.generateTable(map, "Random Keys", "Random Values", false)) System.out.println(s);
		
	}
	
	
	
}
