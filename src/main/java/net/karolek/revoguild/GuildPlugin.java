package net.karolek.revoguild;

import lombok.Getter;
import net.karolek.revoguild.commands.GuildAdminCommand;
import net.karolek.revoguild.commands.GuildCommand;
import net.karolek.revoguild.commands.ranking.RankingCommand;
import net.karolek.revoguild.commands.ranking.TopCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.data.Tablist;
import net.karolek.revoguild.listeners.ActionsListener;
import net.karolek.revoguild.listeners.AsyncChatListener;
import net.karolek.revoguild.listeners.AsyncTagListener;
import net.karolek.revoguild.listeners.DamageListener;
import net.karolek.revoguild.listeners.DeathListener;
import net.karolek.revoguild.listeners.ExplodeListener;
import net.karolek.revoguild.listeners.InventoryListener;
import net.karolek.revoguild.listeners.JoinQuitListener;
import net.karolek.revoguild.listeners.LoginListener;
import net.karolek.revoguild.listeners.MoveListener;
import net.karolek.revoguild.listeners.TeleportListener;
import net.karolek.revoguild.listeners.UptakeListener;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.nametags.NameTagMode;
import net.karolek.revoguild.store.Store;
import net.karolek.revoguild.store.StoreMode;
import net.karolek.revoguild.store.modes.StoreMySQL;
import net.karolek.revoguild.store.modes.StoreSQLITE;
import net.karolek.revoguild.tablist.TabLowUpdateTask;
import net.karolek.revoguild.tablist.TabThread;
import net.karolek.revoguild.tasks.CheckValidityTask;
import net.karolek.revoguild.utils.BlockUtil;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.TimeUtil;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class GuildPlugin extends JavaPlugin {

	@Getter
	private static GuildPlugin	plugin;
	@Getter
	private static Store			store		= null;

	private boolean				enabled	= false;

	@Override
	public void onEnable() {
		plugin = this;
		saveDefaultConfig();

		Config.reloadConfig();
		Lang.reloadLang();
		Tablist.reloadTablist();

		if (!Config.ENABLED) {
			Logger.info("This plugin is not activated in the configuration!", "To activate it, set the value 'enabled' to true!", "Plugin will be disabled!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		if (!registerDatabase()) {
			Logger.info("Can not connect to a MySQL server!", "Plugin will be disabled!");
			store = null;
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}

		Manager.load();

		registerCommands();
		registerListeners();
		registerTasks();
		registerOthers();
		enabled = true;

	}

	@Override
	public void onDisable() {
		if (enabled) {
			Manager.unload();

			if (store != null)
				store.disconnect();

		}

		enabled = false;

		plugin = null;

	}

	protected boolean registerDatabase() {
		Logger.info("Register database...");
		switch (StoreMode.getByName(Config.DATABASE_MODE)) {
			case MYSQL:
				store = new StoreMySQL(Config.DATABASE_MYSQL_HOST, Config.DATABASE_MYSQL_PORT, Config.DATABASE_MYSQL_USER, Config.DATABASE_MYSQL_PASS, Config.DATABASE_MYSQL_NAME, Config.DATABASE_TABLEPREFIX);
				break;
			case SQLITE:
				store = new StoreSQLITE(Config.DATABASE_SQLITE_NAME, Config.DATABASE_TABLEPREFIX);
				break;
			default:
				Logger.warning("Value of databse mode is not valid! Using SQLITE as database!");
				store = new StoreSQLITE(Config.DATABASE_SQLITE_NAME, Config.DATABASE_TABLEPREFIX);
				break;
		}
		boolean conn = store.connect();
		if (conn) {
			store.update(true, "CREATE TABLE IF NOT EXISTS `{P}guilds` (" + (store.getStoreMode() == StoreMode.MYSQL ? "`id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") + "`tag` varchar(4) NOT NULL,`name` varchar(32) NOT NULL,`owner` varchar(36) NOT NULL,`leader` varchar(36) NOT NULL,`cuboidWorld` varchar(32) NOT NULL,`cuboidX` int(10) NOT NULL,`cuboidZ` int(10) NOT NULL,`cuboidSize` int(10) NOT NULL,`homeWorld` varchar(32) NOT NULL,`homeX` int(10) NOT NULL,`homeY` int(10) NOT NULL,`homeZ` int(10) NOT NULL,`lives` int(2) NOT NULL DEFAULT '3',`createTime` bigint(13) NOT NULL DEFAULT '0',`expireTime` bigint(13) NOT NULL DEFAULT '0',`lastTakenLifeTime` bigint(13) NOT NULL DEFAULT '0',`pvp` int(1) NOT NULL DEFAULT '0');");
			store.update(true, "CREATE TABLE IF NOT EXISTS `{P}members` (" + (store.getStoreMode() == StoreMode.MYSQL ? "`id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") + "`uuid` varchar(36) NOT NULL,`tag` varchar(4) NOT NULL);");
			store.update(true, "CREATE TABLE IF NOT EXISTS `{P}treasures` (" + (store.getStoreMode() == StoreMode.MYSQL ? "`id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") + "`tag` varchar(4) NOT NULL,`content` longtext NOT NULL);");
			store.update(true, "CREATE TABLE IF NOT EXISTS `{P}treasure_users` (" + (store.getStoreMode() == StoreMode.MYSQL ? "`id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") + "`uuid` varchar(36) NOT NULL,`tag` varchar(4) NOT NULL);");
			store.update(true, "CREATE TABLE IF NOT EXISTS `{P}alliances` (" + (store.getStoreMode() == StoreMode.MYSQL ? "`id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") + "`guild_1` varchar(4) NOT NULL,`guild_2` varchar(4) NOT NULL);");
			store.update(true, "CREATE TABLE IF NOT EXISTS `{P}users` (" + (store.getStoreMode() == StoreMode.MYSQL ? "`id` int(10) NOT NULL PRIMARY KEY AUTO_INCREMENT," : "`id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,") + "`uuid` varchar(36) NOT NULL,`points` int(10) NOT NULL,`kills` int(10) NOT NULL,`deaths` int(10) NOT NULL,`timePlay` int(10) NOT NULL);");
		}
		return conn;
	}

	protected void registerCommands() {
		Logger.info("Register commands...");
		Manager.COMMAND.register(new GuildCommand());
		Manager.COMMAND.register(new GuildAdminCommand());
		Manager.COMMAND.register(new RankingCommand());
		Manager.COMMAND.register(new TopCommand());
	}

	protected void registerListeners() {
		Logger.info("Register listeners...");
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new ActionsListener(), this);
		pm.registerEvents(new ExplodeListener(), this);
		pm.registerEvents(new MoveListener(), this);
		pm.registerEvents(new TeleportListener(), this);
		pm.registerEvents(new JoinQuitListener(), this);
		pm.registerEvents(new InventoryListener(), this);
		pm.registerEvents(new DamageListener(), this);
		pm.registerEvents(new UptakeListener(), this);
		pm.registerEvents(new AsyncChatListener(), this);
		pm.registerEvents(new DeathListener(), this);
		if (Config.TABLIST_ENABLED)
			pm.registerEvents(new LoginListener(), this);
		if (NameTagMode.getByName(Config.TAG_MODE).equals(NameTagMode.TAG_API))
			pm.registerEvents(new AsyncTagListener(), this);
	}

	protected void registerTasks() {
		Logger.info("Register tasks...");
		new CheckValidityTask().runTaskTimerAsynchronously(this, TimeUtil.HOUR.getTick(3), TimeUtil.HOUR.getTick(Config.TIME_CHECK));
		//new TablistUpdateTask().runTaskTimerAsynchronously(this, 20L, TimeUtil.SECOND.getTick(Config.TABLIST_REFRESH_INTERVAL));; // FUCK IT
<<<<<<< HEAD
		new TabThread();
		new TabLowUpdateTask().runTaskTimerAsynchronously(this, 20L, TimeUtil.SECOND.getTick(Config.TABLIST_REFRESH_INTERVAL));
=======
>>>>>>> origin/master
	}

	protected void registerOthers() {
		Logger.info("Register others...");
		if (Config.TNT_DURABILITY_ENABLED)
			for (String s : Config.TNT_DURABILITY_BLOCKS)
				BlockUtil.setDurability(s.split(" ")[0], Float.parseFloat(s.split(" ")[1]));
	}

}
