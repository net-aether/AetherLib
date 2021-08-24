package net.aether.lib.data;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import net.aether.lib.annotation.Since;

/**
 * The default implementation of {@link Queue}<br><br>
 * 
 * <b>WARNING!</b> not thread-safe!<br>
 * Use {@link SynchronizedQueue} if you need a thread-safe implementation of {@link Queue}
 * 
 * @author Cheos
 * @see {@link SynchronizedQueue}
 */
@Since(V0_0_1)
public class SimpleQueue<T> implements Queue<T> {
	private final List<QueueElement<T>> queue;
	
	/**
	 * Instantiates a new, empty {@link SimpleQueue}
	 */
	public SimpleQueue() { queue = new ArrayList<>(); }
	
	/**
	 * Instantiates a new {@link SimpleQueue} containing all elements of <b>of</b>
	 * 
	 * @param of
	 */
	public SimpleQueue(List<T> of) {
		Wrapper<Integer> i = Wrapper.wrap(-1);
		queue = of.stream().map(t -> new QueueElement<>(i.set(i.get() + 1).get(), t)).collect(Collectors.toList());
	}
	
	@Override
	public boolean insert(T t, int idx) {
		if(idx >= queue.size() || idx < 0) return false;
		
		Wrapper<Boolean> success = Wrapper.wrap(true);
		List<QueueElement<T>> before = new ArrayList<>(queue.subList(0, idx));
		List<QueueElement<T>> after = new ArrayList<>(queue.subList(idx, queue.size()));
		
		success.set(success.get() && before.add(new QueueElement<>(idx, t)));
		after.forEach(qe -> success.set(success.get() && qe.pushUp()));
		success.set(success.get() && before.addAll(after));
		
		if (success.get()) { // TODO: make more efficient... for loop, remove from end and add after -> before sublist is unnecessary then
			queue.clear();
			queue.addAll(before);
			queue.sort((qe1, qe2) -> qe1.pos - qe2.pos);
		}
		
		return success.get();
	}

	@Override
	public boolean push(T t) {
		return queue.add(new QueueElement<>(queue.size(), t));
	}

	@Override
	public T pop() {
		T pop = queue.remove(0).t;
		queue.forEach(QueueElement::pushDown);
		return pop;
	}

	@Override
	public T get(int idx) {
		if(idx >= queue.size() || idx < 0) return null;
		
		return queue.get(idx).t;
	}

	@Override
	public T remove(int idx) {
		if(idx >= queue.size() || idx < 0) return null;

		List<QueueElement<T>> before = queue.subList(0, idx);
		List<QueueElement<T>> after = queue.subList(idx, queue.size());
		T remove = after.remove(0).t;
		
		after.forEach(QueueElement::pushDown);
		before.addAll(after);
		queue.clear();
		queue.addAll(before);
		queue.sort((qe1, qe2) -> qe1.pos - qe2.pos);
		
		return remove;
	}

	@Override
	public T remove(T t) {
		int idx = queue.indexOf(t);
		return idx == -1 ? null : remove(idx);
	}
	
	private static final class QueueElement<T> {
		private int pos;
		private final T t;
		
		QueueElement(int pos, T t) {
			this.pos = pos;
			this.t = t;
		}
		
		boolean pushDown() {
			if(pos <= 0)
				return false;
			pos--;
			return true;
		}
		
		boolean pushUp() {
			pos++;
			return true;
		}
	}
}
