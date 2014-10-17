package net.karolek.revoguild;

import lombok.Getter;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.store.Store;
import net.karolek.revoguild.store.StoreMode;
import net.karolek.revoguild.store.modes.StoreMySQL;
import net.karolek.revoguild.utils.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class GuildPlugin extends JavaPlugin {

	@Getter
	private static GuildPlugin	plugin;
	@Getter
	private static Store store;

	@Override
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();

		Config.loadConfig();
		
		if (!Config.ENABLED) {
			Logger.info("This plugin is not activated in the configuration!", "To activate it, set the value 'enabled' to true!", "Plugin will be disabled!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		if(!registerDatabase()) {
			Logger.info("Can not connect to a MySQL server!", "Plugin will be disabled!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		registerCommands();
		registerListeners();
		registerTasks();
		
		Manager.load();

	}

	@Override
	public void onDisable() {
		
		Config.saveConfig();
		
		Manager.unload();
		
		plugin = null;

	}

	protected boolean registerDatabase() {
		Logger.info("Register database...");
		switch (StoreMode.getByName(Config.DATABASE_MODE)) {
			case MYSQL:
			case SQLITE:
			default:
				store = new StoreMySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASS, Config.DATABASE_MYSQL_NAME, Config.DATABASE_TABLEPREFIX);
				return store.connect();
		}
	}
	

	protected void registerCommands() {
		Logger.info("Register commands...");
	}

	protected void registerListeners() {
		Logger.info("Register listeners...");
	}

	protected void registerTasks() {
		Logger.info("Register tasks...");
	}

}
