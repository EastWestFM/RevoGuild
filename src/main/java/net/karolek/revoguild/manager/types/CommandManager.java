package net.karolek.revoguild.manager.types;

import java.util.HashMap;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.SimplePluginManager;

import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.utils.Reflection;
import net.karolek.revoguild.utils.Reflection.FieldAccessor;

public class CommandManager implements IManager {
	
	private static CommandMap cmdMap = null;
	private static HashMap<String, Command> commands = null;

	@Override
	public void enable() throws Exception {
			FieldAccessor<SimpleCommandMap> f = Reflection.getField(SimplePluginManager.class, "commandMap", SimpleCommandMap.class);
			cmdMap = f.get(Bukkit.getServer().getPluginManager());
			commands = new HashMap<>();
	}

	@Override
	public void disable() {		
		for(Entry<String, Command> en : commands.entrySet())
			cmdMap.getCommand(en.getKey()).unregister(cmdMap);
		
		cmdMap = null;
		commands.clear();
		commands = null;
	}
	
	public void register(Command cmd, String fallback) {
		cmdMap.register(fallback, cmd);
		commands.put(cmd.getName(), cmd);
	}
	
	public void register(Command cmd) {
		register(cmd, cmd.getName());
	}
	
	

}
