import java.text.SimpleDateFormat;
import java.util.*;

import net.aether.lib.debug.DebugTimer;
import net.aether.lib.misc.ArrayUtils;
import net.aether.lib.misc.Formatter;

public class Test {
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss.SSS");
		
		DebugTimer dt = new DebugTimer(1);
		
		Map<String, String> map = new HashMap<>();
		Random rnjesus = new Random();
		
		
		for (int i = 0; i < 10; i++) map.put("" + rnjesus.nextInt(0xFF), "" + rnjesus.nextInt(0xFF));
		
		for (String s : Formatter.generateTable(map, "Random Keys", "Random Values", false))
			System.out.println(s);

		String[] test0 = { "Hi", "Hallo", "Hello" };
		String[] test1 = { "Kilix", "Cheos", "World", "test", "TEST" };
		String[] test2 = { "aaa", null, "bbb", "ccc" };

		dt.start();
		
		String[] test = ArrayUtils.mergeStrings(true, '_', test0, test1, test2);

		dt.intermediate();
		
		ArrayUtils.printArray(test);
		
		dt.end();
		
		System.out.println();
		for (String s : test)
			System.out.println(s);
		System.out.println();
		
		System.out.println();
		System.out.printf("Took %.3fms\n", dt.getMicroDiff() / 1000D);
		System.out.printf("First segment took %.3fms\n", dt.getMicroDiff(0) / 1000D);
		System.out.printf("Second segment took %.3fms\n", dt.getMicroDiff(0, true) / 1000D);
		System.out.println(df.format(new Date(dt.getStartMillis())));
		System.out.println(df.format(new Date(dt.getIntermediateMillis(0))));
		System.out.println(df.format(new Date(dt.getEndMillis())));
	}
}
