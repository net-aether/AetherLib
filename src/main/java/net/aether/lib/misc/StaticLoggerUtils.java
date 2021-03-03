package net.aether.lib.misc;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import net.aether.lib.annotation.Since;

@Since(V0_0_1)
public class StaticLoggerUtils {

	public static void sysout(Object message) {
		StackTraceElement ste = new Throwable().getStackTrace()[1]; // workaround because this need to be at compile time
		String[] name = ste.getClassName().split("\\.");
		System.out.println("[" + Thread.currentThread().getName() + " > " + name[name.length - 1] + ":" + ste.getLineNumber() + "] " + message);
	}
	
}
