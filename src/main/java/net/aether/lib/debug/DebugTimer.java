package net.aether.lib.debug;

/**
 * A misc class to help with optimizing code segments.
 * 
 * @author Cheos
 */
public class DebugTimer {
	protected boolean start = false,
			          end   = false;
	protected long startMillis,
	               startNanos,
	               endNanos;
	protected long[] inter;
	protected int  rnd;
	
	/**
	 * Initializes a new {@link DebugTimer} which can be very handy to improve performance.<br>
	 * @param maxIntermediates specifies how many intermediates may be stored - calling {@link #intermediate()} more than <b>maxIntermediates</b> times will result in an {@link ArrayIndexOutOfBoundsException}.
	 */
	public DebugTimer(int maxIntermediates) {
		inter = new long[maxIntermediates];
	}
	
	/**
	 * Initializes a new {@link DebugTimer} which can be very handy to improve performance.<br>
	 * This will behave the same as calling {@link #DebugTimer(int) DebugTimer(10)}.
	 */
	public DebugTimer() {
		this(10);
	}
	
	/**
	 * Starts this timer's time recording.<br>
	 * This method can only be called once for each {@link DebugTimer}.
	 */
	public void start() {
		if (start) return;
		start = true;
		startMillis = System.currentTimeMillis();
		startNanos  = System.nanoTime();
	}
	
	/**
	 * Sets a new intermediate timepoint for this timer.<br>
	 * This method can not be called before {@link #start()} and after {@link #end()} has been called.
	 * Calling this method more often than <b>maxIntermediates</b> times, as specified in {@link #DebugTimer(int)}, will result in an {@link ArrayIndexOutOfBoundsException}.
	 */
	public void intermediate() {
		if (end || !start) return;
		inter[rnd++] = System.nanoTime();
	}
	
	/**
	 * Ends this timer's time recording.<br>
	 * This method can only be called once and must be called after {@link #start()} has been called.
	 */
	public void end() {
		if (end || !start) return;
		endNanos = System.nanoTime();
		end = true;
	}
	
	/**
	 * @return The time difference between two recorded timestamps, <b>intermediate0</b> and <b>intermediate1</b> in microseconds.
	 */
	public long getMicroDiff(int intermediate0, int intermediate1) {
		if (intermediate0 == intermediate1) return 0;
		if (intermediate0 >  intermediate1) {
			int temp      =  intermediate0;
			intermediate0 =  intermediate1;
			intermediate1 =  temp;
		}
		return rnd <= intermediate1 ? 0 : microDiff(inter[intermediate0], inter[intermediate1]);
	}
	
	/**
	 * @return The time difference between two recorded timestamps, <b>intermediate0</b> and <b>intermediate1</b> in microseconds.
	 */
	public double getMicroDiffD(int intermediate0, int intermediate1) {
		if (intermediate0 == intermediate1) return 0;
		if (intermediate0 >  intermediate1) {
			int temp      =  intermediate0;
			intermediate0 =  intermediate1;
			intermediate1 =  temp;
		}
		return rnd <= intermediate1 ? 0 : microDiffD(inter[intermediate0], inter[intermediate1]);
	}
	
	/**
	 * Equal to calling {@link #getMicroDiff(int, boolean) getMicroDiff(intermediate, false)}.
	 * @see #getMicroDiff(int, boolean)
	 */
	public long getMicroDiff(int intermediate) {
		return getMicroDiff(intermediate, false);
	}
	
	/**
	 * Equal to calling {@link #getMicroDiff(int, boolean) getMicroDiff(intermediate, false)}.
	 * @see #getMicroDiff(int, boolean)
	 */
	public double getMicroDiffD(int intermediate) {
		return getMicroDiffD(intermediate, false);
	}
	
	/**
	 * @return The time difference between the start of recording and a recorded intermediate timestamp or, if diffToEnd is true, the time difference between a recorded timestamp and the end of recording in microseconds.
	 */
	public long getMicroDiff(int intermediate, boolean diffToEnd) {
		return rnd <= intermediate || (diffToEnd && !end)
				? 0
				: diffToEnd
					? microDiff(inter[intermediate], endNanos)
					: microDiff(startNanos, inter[intermediate]);
	}
	
	/**
	 * @return The time difference between the start of recording and a recorded intermediate timestamp or, if diffToEnd is true, the time difference between a recorded timestamp and the end of recording in microseconds.
	 */
	public double getMicroDiffD(int intermediate, boolean diffToEnd) {
		return rnd <= intermediate || (diffToEnd && !end)
				? 0
				: diffToEnd
					? microDiffD(inter[intermediate], endNanos)
					: microDiffD(startNanos, inter[intermediate]);
	}
	
	/**
	 * @return The time difference between the start and the end of recording in microseconds.
	 */
	public long getMicroDiff() {
		return end ? microDiff(startNanos, endNanos) : 0;
	}
	
	/**
	 * @return The time difference between the start and the end of recording in microseconds.
	 */
	public double getMicroDiffD() {
		return end ? microDiffD(startNanos, endNanos) : 0;
	}
	
	private long microDiff(long start, long end) {
		return (long) Math.floor((end - start) / 1000D);
	}
	
	private double microDiffD(long start, long end) {
		return (end - start) / 1000D;
	}
	
	/**
	 * @return The time of the start of recording in milliseconds since the start of the last epoch.
	 */
	public long getStartMillis() {
		return startMillis;
	}
	
	/**
	 * @return The time of an intermediate timestamp in milliseconds since the start of the last epoch.
	 */
	public long getIntermediateMillis(int intermediate) {
		if (rnd <= intermediate) return 0;
		return getOffsetMillis(inter[intermediate] - startNanos);
	}

	/**
	 * @return The time of the end of recording in milliseconds since the start of the last epoch.
	 */
	public long getEndMillis() {
		return getOffsetMillis(endNanos - startNanos);
	}
	
	private long getOffsetMillis(long offsetNanos) {
		return startMillis + (long) (offsetNanos / 1_000_000D);
	}
	
	/**
	 * @return The current amount of intermediate timestamps.
	 */
	public int getIntermediateAmount() {
		return rnd;
	}
}
