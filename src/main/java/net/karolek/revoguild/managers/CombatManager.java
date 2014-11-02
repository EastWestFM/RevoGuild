package net.karolek.revoguild.managers;

import java.text.DecimalFormat;
import java.util.HashMap;

import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.utils.TimeUtil;

import org.bukkit.entity.Player;

import lombok.Getter;

public class CombatManager {

	@Getter
	private static final HashMap<String, Long>	combats	= new HashMap<>();

	public static void createPlayer(Player p) {
		combats.put(p.getName(), 0L);
	}

	public static void removePlayer(Player p) {
		combats.remove(p.getName());
	}

	public static long getLastFight(Player p) {
		return combats.get(p.getName());
	}

	public static void setLastFight(Player p) {
		combats.put(p.getName(), System.currentTimeMillis());
	}

	public static boolean isInFight(Player p) {
		return (System.currentTimeMillis() - combats.get(p.getName()) < TimeUtil.SECOND.getTime(Config.ESCAPE_TIME));
	}

	public static boolean wasInFight(Player p) {
		return (System.currentTimeMillis() - combats.get(p.getName()) - 1000L < TimeUtil.SECOND.getTime(Config.ESCAPE_TIME));
	}
	
	public static long getTimeToEnd(Player p) {
		long actual = System.currentTimeMillis();
		Long time = combats.get(p.getName());
		if(time == null || time == 0) return 0;
		long d = actual - time;
		long t = TimeUtil.SECOND.getTime(Config.ESCAPE_TIME) - d;
		
		return t;
	}
	
	public static String getTime(Player p) {
		double seconds = getTimeToEnd(p) / 1000.0;
		DecimalFormat df = new DecimalFormat("#.###");
		return df.format(seconds);
	}

}
