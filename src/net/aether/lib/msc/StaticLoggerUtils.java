package net.aether.lib.msc;

public class StaticLoggerUtils {

	public static void sysout(Object message) {
		StackTraceElement ste = new Throwable().getStackTrace()[1]; // workaround because this need to be at compile time
		String[] name = ste.getClassName().split("\\.");
		System.out.println("[" + Thread.currentThread().getName() + " > " + name[name.length - 1] + ":" + ste.getLineNumber() + "] " + message);
	}
	
}
