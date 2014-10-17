package net.karolek.revoguild.data;

import java.lang.reflect.Field;

import net.karolek.revoguild.GuildPlugin;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private static final String	prefix					= "config.";

	public static boolean			ENABLED					= false;
	public static String				DATABASE_MODE			= "mysql";
	public static String				DATABASE_TABLEPREFIX	= "ks_";
	public static String				DATABASE_MYSQL_HOST	= "localhost";
	public static int					DATABASE_MYSQL_PORT	= 3306;
	public static String				DATABASE_MYSQL_USER	= "root";
	public static String				DATABASE_MYSQL_PASS	= "";
	public static String				DATABASE_MYSQL_NAME	= "minecraft";

	public static void loadConfig() {
		try {
			FileConfiguration c = GuildPlugin.getPlugin().getConfig();

			for (Field f : Config.class.getFields()) {

				if (c.isSet(prefix + f.getName().toLowerCase().replace("_", ".")))
					f.set(null, c.get(prefix + f.getName().toLowerCase().replace("_", ".")));
				System.out.println(f.getName() + " -> " + f.get(null));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveConfig() {
		try {
			FileConfiguration c = GuildPlugin.getPlugin().getConfig();
			for (Field f : Config.class.getFields()) {
				c.set(prefix + f.getName().toLowerCase().replace("_", "."), f.get(null));
			}
			GuildPlugin.getPlugin().saveConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reloadConfig() {
		GuildPlugin.getPlugin().reloadConfig();
		loadConfig();
		saveConfig();
	}

}
