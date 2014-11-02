package net.karolek.revoguild.utils;

import org.apache.commons.lang.Validate;

public class RandomUtil {

	private static final java.util.Random	rand	= new java.util.Random();

	public static int getRandInt(int min, int max) throws IllegalArgumentException {
		Validate.isTrue(max > min, "Max can't be smaller than min!");
		return rand.nextInt(max - min + 1) + min;
	}

	public static Double getRandDouble(double min, double max) throws IllegalArgumentException {
		Validate.isTrue(max > min, "Max can't be smaller than min!");
		return (rand.nextDouble() * (max - min)) + min;
	}

	public static Float getRandFloat(float min, float max) throws IllegalArgumentException {
		Validate.isTrue(max > min, "Max can't be smaller than min!");
		return (rand.nextFloat() * (max - min)) + min;
	}

	public static boolean getChance(double chance) {
		return (chance >= 100) || (chance >= getRandDouble(0, 100));
	}

}
