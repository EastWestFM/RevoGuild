package net.karolek.revoguild.managers;

import java.util.HashMap;

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
		return (System.currentTimeMillis() - combats.get(p.getName()) < 20 * 1000L);
	}

	public static boolean wasInFight(Player p) {
		return (System.currentTimeMillis() - combats.get(p.getName()) - 1000L < 20 * 1000L);
	}

}
