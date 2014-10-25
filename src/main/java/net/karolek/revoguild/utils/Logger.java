package net.karolek.revoguild.utils;

import java.util.logging.Level;

import org.bukkit.Bukkit;

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

	public static void exception(Throwable cause) {
		if (cause == null) {return;}
		if (cause.getStackTrace() == null)
			cause.printStackTrace();
		exception(cause.getMessage(), cause.getStackTrace());
	}

	private static boolean exception(String cause, StackTraceElement[] ste) {
		severe("");
		severe("An error occurred while running the plugin");
		severe("");
		severe("Version Information:");
		severe("  RevoGuild: " + GuildPlugin.getPlugin().getDescription().getVersion());
		severe("  Bukkit: " + Bukkit.getBukkitVersion());
		severe("  Java: " + System.getProperty("java.version"));
		severe("  Thread: " + Thread.currentThread());
		severe("  Running CraftBukkit: " + Bukkit.getServer().getClass().getName().equals("org.bukkit.craftbukkit.CraftServer"));
		severe("");
		if ((cause == null) || (ste == null)) {
			severe("Stack trace: no/empty exception given, dumping current stack trace instead!");
			return true;
		}
		severe("Stack trace: ");
		severe("Caused by: " + cause);
		for (StackTraceElement st : ste) {
			severe("    at " + st.toString());
		}
		severe("");
		severe("End of Error.");
		severe("");
		return false;
	}

}
