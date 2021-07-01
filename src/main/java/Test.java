import java.text.SimpleDateFormat;
import java.util.*;

import net.aether.lib.data.Queue;
import net.aether.lib.data.SimpleQueue;
import net.aether.lib.debug.DebugTimer;
import net.aether.lib.annotation.EventListener;
import net.aether.lib.events.EventDispatcher;
import net.aether.lib.events.MultiEventDispatcher;
import net.aether.lib.events.RestrictedEventDispatcher;
import net.aether.lib.events.SimpleEventDispatcher;

public class Test {
	
	@SuppressWarnings("all")
	public static void main(String[] args) {
		if (args.length > 0) switch (args[0]) {
			case "kilix":
				kilixMain(args);
				break;
			case "cheos":
				cheosMain(args);
		}
		
	}
	
	@EventListener
	public static void test(String event) {
		System.out.println("Received static event: " + event);
	}
	
	public static void kilixMain(String[] args) {
		EventDispatcher ed = new RestrictedEventDispatcher();
		
		ed.register(Test.class);
		
		ed.triggerEvent("Static!");
		ed.triggerEvent("Hallo");
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
