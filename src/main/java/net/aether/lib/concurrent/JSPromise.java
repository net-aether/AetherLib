package net.aether.lib.concurrent;

import net.aether.lib.annotation.Since;

import java.util.*;
import java.util.function.*;

public class JSPromise<Type> {
	
	public enum State {PENDING, SUCCESS, ERROR}
	
	private final Set<Consumer<Type>> successSet = new HashSet<>();
	private final Set<ErrorConsumer> handlerSet = new HashSet<>();
	
	private final Consumer<Type> success = type -> {
		completed = true;
		completionValue = type;
		successSet.forEach(it -> it.accept(type));
	};
	private final ErrorConsumer fail = error -> {
		completed = true;
		completionError = error;
		handlerSet.forEach(it -> it.accept(error));
	};
	
	private boolean completed = false;
	private Type completionValue = null;
	private Throwable completionError = null;
	
	private JSPromise() {}
	
	public State getState() {
		if (! completed) return State.PENDING;
		if (completionError != null) return State.ERROR;
		return State.SUCCESS;
	}
	
	public <NewType> JSPromise<NewType> then(Function<Type, NewType> then) {
		if (completed) {
			if (completionError != null) return JSPromise.failed(completionError);
			return JSPromise.completed(then.apply(completionValue));
		}
		
		JSPromise<NewType> out = new JSPromise<>();
		successSet.add(val -> out.success.accept(then.apply(val)));
		
		return out;
	}
	
	public JSPromise<Void> then(Consumer<Type> then) {
		if (completed) {
			if (completionError != null) return JSPromise.failed(completionError);
			then.accept(completionValue);
			return JSPromise.completed(null);
		}
		
		JSPromise<Void> out = new JSPromise<>();
		successSet.add(val -> {
			then.accept(val);
			out.success.accept(null);
		});
		
		return out;
	}
	
	public void catchError(ErrorConsumer handler) {
		if (completed) {
			if (completionError != null) handler.accept(completionError);
			return;
		}
		handlerSet.add(handler);
	}
	
	public static <R> JSPromise<R> completeAsync(BiConsumer<Consumer<R>, ErrorConsumer> executor) {
		JSPromise<R> out = new JSPromise<>();
		
		Thread execThread = new Thread(() -> executor.accept(out.success, out.fail), "PromiseThread-" + UUID.randomUUID());
		execThread.setUncaughtExceptionHandler((thread, err) -> out.fail.accept(err));
		execThread.start();
		
		return out;
	}
	
	public static <R> JSPromise<R> completeNow(BiConsumer<Consumer<R>, ErrorConsumer> executor) {
		JSPromise<R> out = new JSPromise<>();
		executor.accept(out.success, out.fail);
		return out;
	}
	
	public static <R, E extends Throwable> JSPromise<R> completed(R result) {
		JSPromise<R> out = new JSPromise<>();
		out.completed = true;
		out.completionValue = result;
		return out;
	}
	
	public static <R> JSPromise<R> failed(Throwable error) {
		JSPromise<R> out = new JSPromise<>();
		out.completed = true;
		out.completionError = error;
		return out;
	}
	
	@FunctionalInterface
	public static interface ErrorConsumer extends Consumer<Throwable> {
		public default void empty() { accept(null); }
	}
	
	public String toString() {
		return "JSPromise{" +
				"state=" + getState() +
				", value=" + completionValue +
				", error=" + completionError +
				'}';
	}
}
