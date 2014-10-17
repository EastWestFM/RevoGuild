package net.karolek.revoguild.utils;

import java.util.logging.Level;

import net.karolek.revoguild.GuildPlugin;

public class Logger {

	public static void info(String... logs) {
		for (String s : logs)
			GuildPlugin.getPlugin().getLogger().log(Level.INFO, s);
	}

	public static void warning(String... logs) {
		for (String s : logs)
			GuildPlugin.getPlugin().getLogger().log(Level.WARNING, s);
	}

	public static void severe(String... logs) {
		for (String s : logs)
			GuildPlugin.getPlugin().getLogger().log(Level.SEVERE, s);
	}

}
