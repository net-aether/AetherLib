package net.aether.lib.debug;

import static net.aether.lib.misc.AetherLibVersion.V0_0_1;

import java.util.concurrent.TimeUnit;

import net.aether.lib.annotation.Since;

/**
 * {@link DebugTimer} version 2<br><br>
 * 
 * A misc class to help with optimizing code segments and measuring the execution time of such<br>
 * This timer has little code overhead<br>
 * Reading from this timer is not optimized and should not be done while recording timestamps
 * 
 * @see {@link PrimitiveTimer} for a simple to use, general purpose timer
 * @author Cheos
 */
@Since(V0_0_1)
public class DebugTimer {
	protected boolean started = false,
			          stopped   = false;
//	@Deprecated
//	protected long startMillis,
//	               startNanos,
//	               endNanos;
	protected int  splits;
	protected long[] timestamps; // [0] = start millis, [1] = start nanos, [2] = end nanos, [...] = splits
	
	/**
	 * Initializes a new {@link DebugTimer}<br>
	 * @param splits specifies how many timestamps may be stored
	 */
	public DebugTimer(int splits) {
		this.timestamps = new long[splits + 3]; // additional space for start millis, start nanos, end nanos
	}
	
	/**
	 * Initializes a new {@link DebugTimer}<br>
	 * Equal to calling {@link #DebugTimer(int) DebugTimer(30)}
	 */
	public DebugTimer() {
		this(30);
	}
	
	/**
	 * Starts this timer<br>
	 * This method can be called only once per instance of {@link DebugTimer}
	 */
	public void start() {
		if (this.started) return;
		this.started = true;
		this.timestamps[0] = System.currentTimeMillis();
		this.timestamps[1] = System.nanoTime();
	}
	
	/**
	 * Records a new timestamp to this {@link DebugTimer}<br>
	 * This method must be called after {@link #start()} has been called and before {@link #end()} has been called
	 * @throws ArrayIndexOutOfBoundsException if more splits than specified by <b>splits</b> in {@link #DebugTimer(int)} are recorded
	 */
	public void split() {
		if (this.stopped || !this.started) return;
		this.timestamps[3 + this.splits++] = System.nanoTime();
	}
	
	/**
	 * Sets a new intermediate timepoint for this timer<br>
	 * This method can not be called before {@link #start()} and after {@link #end()} has been called
	 * @throws ArrayIndexOutOfBoundsException if more splits than specified by <b>splits</b> in {@link #DebugTimer(int)} are recorded
	 * @deprecated in favor of {@link #split()}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public void intermediate() {
		if (this.stopped || !this.started) return;
		this.timestamps[3 + this.splits++] = System.nanoTime();
	}
	
	/**
	 * Stops this timer<br>
	 * This method can be called only once per instance of {@link DebugTimer} and must be called after {@link #start()}
	 */
	public void stop() {
		if (this.stopped || !this.started) return;
		this.timestamps[2] = System.nanoTime();
		this.stopped = true;
	}
	
	/**
	 * Ends this timer's time recording<br>
	 * This method can only be called once and must be called after {@link #start()} has been called
	 * @deprecated in favor of {@link #stop()}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public void end() {
		if (this.stopped || !this.started) return;
		this.timestamps[2] = System.nanoTime();
		this.stopped = true;
	}
	
	/**
	 * Resets this timer<br>
	 * All recorded values will be cleared<br>
	 * After calling {@link #reset()}, the timer can be started with {@link #start()}, again
	 */
	public void reset() {
		for (int i = 0; i < this.timestamps.length; i++)
			this.timestamps[i] = 0;
		this.splits = 0;
		this.started = false;
		this.stopped = false;
	}
	
	/**
	 * Gets a split
	 * @param split the split to get, 0 being the first split
	 * @return a split out of all recorded values, 0 being the first split
	 * @see {@link #getFirstSplit()}
	 * @see {@link #getLastSplit()}
	 * @throws IllegalArgumentException if <b>split</b> is not available
	 */
	public Split getSplit(int split) {
		if (split < this.splits)
			throw new IllegalArgumentException("Invalid split " + split + ", only " + this.splits + " splits available.");
		if (split == 0 && this.splits == 0)
			return getTotalSplit();
		if (split == 0)
			return new Split(this.timestamps[0], this.timestamps[1], this.timestamps[3]);
		if (split == this.splits)
			return new Split(this.timestamps[0], this.timestamps[split + 2], this.timestamps[2]);
		return new Split(this.timestamps[0], this.timestamps[split + 2], this.timestamps[split + 3]);
	}
	
	/**
	 * Gets a range of splits
	 * @param fromSplit the split to use as start, 0 being the first split
	 * @param splits the amount of splits to include. setting this to 1 equals to calling {@link #getSplit(int) getSplit(fromSplit)}
	 * @return a split spanning over the timespan of all <b>splits</b> splits, starting at <b>fromSplit</b>
	 * @throws IllegalArgumentException if the split range is not avaliable or invalid
	 */
	public Split getSplits(int fromSplit, int splits) {
		if (fromSplit + splits < this.splits)
			throw new IllegalArgumentException("Invalid split range " + fromSplit + "-" + (fromSplit + splits) + ", only " + this.splits + " splits available.");
		if (splits < 1)
			throw new IllegalArgumentException("Split range cannot have a length smaller than 1");
		if (fromSplit == 0 && splits == this.splits)
			return getTotalSplit();
		if (fromSplit == 0)
			return new Split(this.timestamps[0], this.timestamps[1], this.timestamps[splits + 3]);
		if (fromSplit + splits == this.splits)
			return new Split(this.timestamps[0], this.timestamps[fromSplit + 2], this.timestamps[2]);
		return new Split(this.timestamps[0], this.timestamps[fromSplit + 2], this.timestamps[fromSplit + splits + 3]);
	}
	
	/**
	 * Gets the first split
	 * @return the first available split
	 * @see {@link #getSplit(int)} equal to calling {@link #getSplit(int) getSplit(0)}
	 */
	public Split getFirstSplit() {
		return getSplit(0);
	}
	
	/**
	 * Gets the last split
	 * @return the last available split
	 * @see {@link #getSplit(int)} equal to calling {@link #getSplit(int)} with {@link #getSplitsCount() getSplitsCount() - 1}
	 */
	public Split getLastSplit() {
		return getSplit(this.splits);
	}
	
	/**
	 * Gets the split spanning from start to stop
	 * @return a split, spanning from the start time to the end time of this timer
	 */
	public Split getTotalSplit() {
		return new Split(this.timestamps[0], this.timestamps[1], this.timestamps[2]);
	}
	
	/**
	 * @return The time difference between two recorded timestamps, <b>intermediate0</b> and <b>intermediate1</b> in microseconds
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getMicroDiff(int intermediate0, int intermediate1) {
		return getSplits(intermediate0, intermediate1 - intermediate0).getDuration(TimeUnit.MICROSECONDS);
//		if (intermediate0 == intermediate1) return 0;
//		if (intermediate0 >  intermediate1) {
//			int temp      =  intermediate0;
//			intermediate0 =  intermediate1;
//			intermediate1 =  temp;
//		}
//		return this.splits <= intermediate1 ? 0 : microDiff(this.timestamps[intermediate0], this.timestamps[intermediate1]);
	}
	
	/**
	 * @return The time difference between two recorded timestamps, <b>intermediate0</b> and <b>intermediate1</b> in microseconds
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public double getMicroDiffD(int intermediate0, int intermediate1) {
		return getSplits(intermediate0, intermediate1 - intermediate0).getDuration() / 1000D;
//		if (intermediate0 == intermediate1) return 0;
//		if (intermediate0 >  intermediate1) {
//			int temp      =  intermediate0;
//			intermediate0 =  intermediate1;
//			intermediate1 =  temp;
//		}
//		return this.splits <= intermediate1 ? 0 : microDiffD(this.timestamps[intermediate0], this.timestamps[intermediate1]);
	}
	
	/**
	 * Equal to calling {@link #getMicroDiff(int, boolean) getMicroDiff(intermediate, false)}
	 * @see #getMicroDiff(int, boolean)
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getMicroDiff(int intermediate) {
		return getMicroDiff(intermediate, false);
	}
	
	/**
	 * Equal to calling {@link #getMicroDiff(int, boolean) getMicroDiff(intermediate, false)}
	 * @see #getMicroDiff(int, boolean)
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public double getMicroDiffD(int intermediate) {
		return getMicroDiffD(intermediate, false);
	}
	
	/**
	 * @return The time difference between the start of recording and a recorded intermediate timestamp or, if diffToEnd is true, the time difference between a recorded timestamp and the end of recording in microseconds
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getMicroDiff(int intermediate, boolean diffToEnd) {
		return diffToEnd
				? getSplits(intermediate, this.splits - intermediate).getDuration(TimeUnit.MICROSECONDS)
				: getSplits(0, intermediate).getDuration(TimeUnit.MICROSECONDS);
//		return this.splits <= intermediate || (diffToEnd && !this.stopped)
//				? 0
//				: diffToEnd
//					? microDiff(this.timestamps[intermediate], this.endNanos)
//					: microDiff(this.startNanos, this.timestamps[intermediate]);
	}
	
	/**
	 * @return The time difference between the start of recording and a recorded intermediate timestamp or, if diffToEnd is true, the time difference between a recorded timestamp and the end of recording in microseconds
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public double getMicroDiffD(int intermediate, boolean diffToEnd) {
		return diffToEnd
				? getSplits(intermediate, this.splits - intermediate).getDuration() / 1000D
				: getSplits(0, intermediate).getDuration() / 1000D;
//		return this.splits <= intermediate || (diffToEnd && !this.stopped)
//				? 0
//				: diffToEnd
//					? microDiffD(this.timestamps[intermediate], this.endNanos)
//					: microDiffD(this.startNanos, this.timestamps[intermediate]);
	}
	
	/**
	 * @return The time difference between the start and the end of recording in microseconds
	 * @deprecated in favor of {@link #getSplit(int)}, {@link #getTotalSplit()} and {@link #getSplits(int, int)}
	 * @see {@link #getTotalSplit()}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getMicroDiff() {
		return getTotalSplit().getDuration(TimeUnit.MICROSECONDS);
//		return this.stopped ? microDiff(this.startNanos, this.endNanos) : 0;
	}
	
	/**
	 * @return The time difference between the start and the end of recording in microseconds
	 * @deprecated in favor of {@link #getSplit(int)}, {@link #getTotalSplit()} and {@link #getSplits(int, int)}
	 * @see {@link #getTotalSplit()}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public double getMicroDiffD() {
		return getTotalSplit().getDuration() / 1000D;
//		return this.stopped ? microDiffD(this.startNanos, this.endNanos) : 0;
	}

//	@Deprecated(forRemoval = true, since = "v0.0.1")
//	private long microDiff(long start, long end) {
//		return (long) Math.floor((end - start) / 1000D);
//	}
//
//	@Deprecated(forRemoval = true, since = "v0.0.1")
//	private double microDiffD(long start, long end) {
//		return (end - start) / 1000D;
//	}
	
	/**
	 * @return The time of the start of recording in milliseconds since the start of the last epoch
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getFirstSplit()}
	 * @see {@link #getTotalSplit()}
	 * @see {@link Split#getStart()}
	 * @see {@link Split#getStart(TimeUnit)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getStartMillis() {
		return getTotalSplit().getStart(TimeUnit.MILLISECONDS);
//		return this.startMillis;
	}
	
	/**
	 * @return The time of an intermediate timestamp in milliseconds since the start of the last epoch
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link Split#getEnd()}
	 * @see {@link Split#getEnd(TimeUnit) Split.getEnd(TimeUnit.MILLISECONDS)}
	 * @see {@link #getSplits(int, int) getSplits(0, intermediate)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getIntermediateMillis(int intermediate) {
		return getSplits(0, intermediate).getEnd(TimeUnit.MILLISECONDS);
//		if (this.splits <= intermediate) return 0;
//		return getOffsetMillis(this.timestamps[intermediate] - this.startNanos);
	}

	/**
	 * @return The time of the end of recording in milliseconds since the start of the last epoch
	 * @deprecated in favor of {@link #getSplit(int)} and {@link #getSplits(int, int)}
	 * @see {@link #getSplit(int)}
	 * @see {@link #getLastSplit()}
	 * @see {@link #getTotalSplit()}
	 * @see {@link Split#getEnd()}
	 * @see {@link Split#getEnd(TimeUnit) Split.getEnd(TimeUnit.MILLISECONDS)}
	 * @see {@link #getSplits(int, int)}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public long getEndMillis() {
		return getTotalSplit().getEnd(TimeUnit.MILLISECONDS);
//		return getOffsetMillis(this.endNanos - this.startNanos);
	}

//	@Deprecated(forRemoval = true, since = "v0.0.1")
//	private long getOffsetMillis(long offsetNanos) {
//		return this.startMillis + (long) (offsetNanos / 1_000_000D);
//	}
	
	/**
	 * Gets the amount of available splits
	 * @return The current amount of splits (equals to intermediate timestamps + 1)
	 */
	public int getSplitsCount() {
		return this.splits + 1;
	}
	
	/**
	 * @return The current amount of intermediate timestamps
	 * @deprecated in favor of {@link #getSplitsCount()}
	 */
	@Deprecated(forRemoval = true, since = "v0.0.1")
	public int getIntermediateAmount() {
		return this.splits + 1;
	}
	
	public static class Split {
		private final long startMillis,
						   startNanos, // this is not jvm's off-scale nanoTime, but nano offset
						   endNanos;  // same here
		
		private Split(long startMillis, long startNanos, long endNanos) {
			this.startMillis = startMillis;
			this.startNanos  = startNanos;
			this.endNanos    = endNanos;
		}
		
		/**
		 * This method is equal to calling {@link getDuration(TimeUnit) getDuration(TimeUnit.NANOSECONDS)} but with less code overhead
		 * @return the duration of this split, in nanoseconds
		 */
		public long getDuration() {
			return this.endNanos - this.startNanos;
		}
		
		/**
		 * @param unit the {@link TimeUnit} this split's duration should be returned in
		 * @return the duration of this split, in the specified {@link TimeUnit}
		 * @see {@link #getDuration()} should be considered if <b>unit</b> will always equal to {@link TimeUnit#NANOSECONDS} as it has less code overhead
		 */
		public long getDuration(TimeUnit unit) {
			return unit.convert(getDuration(), TimeUnit.NANOSECONDS);
		}
		
		/**
		 * This method is equal to calling {@link getStart(TimeUnit) getStart(TimeUnit.NANOSECONDS)} but with less code overhead
		 * @return the start time of this split, in nanoseconds
		 */
		public long getStart() {
			return this.startMillis * 1000L + this.startNanos;
		}
		
		/**
		 * @param unit the {@link TimeUnit} this split's start time should be returned in
		 * @return the start time of this split, in the specified {@link TimeUnit}
		 * @see {@link #getStart()} should be considered if <b>unit</b> will always equal to {@link TimeUnit#NANOSECONDS} as it has less code overhead
		 */
		public long getStart(TimeUnit unit) {
			return unit.convert(this.startMillis, TimeUnit.MILLISECONDS) + unit.convert(this.startNanos, TimeUnit.NANOSECONDS);
		}
		
		/**
		 * This method is equal to calling {@link getEnd(TimeUnit) getEnd(TimeUnit.NANOSECONDS)} but with less code overhead
		 * @return the end time of this split, in nanoseconds
		 */
		public long getEnd() {
			return getStart() + this.endNanos;
		}
		
		/**
		 * @param unit the {@link TimeUnit} this split's duration should be returned in
		 * @return the end time of this split, in the specified {@link TimeUnit}
		 * @see {@link #getEnd()} should be considered if <b>unit</b> will always equal to {@link TimeUnit#NANOSECONDS} as it has less code overhead
		 */
		public long getEnd(TimeUnit unit) {
			return getStart(unit) + unit.convert(getEnd(), TimeUnit.NANOSECONDS);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String toString() {
			return (getDuration() / 1000D) + "\u00B5s";
		}
	}
}
