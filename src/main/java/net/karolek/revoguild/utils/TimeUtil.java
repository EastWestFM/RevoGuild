package net.karolek.revoguild.utils;

public enum TimeUtil {

	TICK(50, 50),
	MILLISECOND(1, 1),
	SECOND(1_000, 1_000),
	MINUTE(60_000, 60),
	HOUR(3_600_000, 60),
	DAY(86_400_000, 24),
	WEEK(604_800_000, 7);

	public static final int	MPT	= 50;
	private final int			time, timeMulti;

	TimeUtil(final int time, final int timeMulti) {
		this.time = time;
		this.timeMulti = timeMulti;
	}

	public int getMulti() {
		return this.timeMulti;
	}

	public int getTime() {
		return this.time;
	}

	public int getTick() {
		return this.time / MPT;
	}

	public int getTime(final int multi) {
		return this.time * multi;
	}

	public int getTick(final int multi) {
		return this.getTick() * multi;
	}

}
