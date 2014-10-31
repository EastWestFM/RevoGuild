package net.karolek.revoguild.managers;

import java.util.HashMap;

import net.karolek.revoguild.utils.Reflection;
import net.karolek.revoguild.utils.Reflection.FieldAccessor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

public class CommandManager {

	private static CommandMap										cmdMap	= null;
	private static final HashMap<String, Command>			commands	= new HashMap<>();
	private static final FieldAccessor<SimpleCommandMap>	f			= Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);

	public static void enable() {
		cmdMap = f.get(Bukkit.getServer().getPluginManager());
	}

	public static void register(Command cmd, String fallback) {
		cmdMap.register(fallback, cmd);
		commands.put(cmd.getName(), cmd);
	}

	public static void register(Command cmd) {
		register(cmd, cmd.getName());
	}

}
