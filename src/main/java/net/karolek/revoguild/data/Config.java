package net.karolek.revoguild.data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.utils.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private static final String	prefix						= "config.";

	public static boolean			ENABLED						= false;

	public static String				DATABASE_MODE				= "mysql";
	public static String				DATABASE_TABLEPREFIX		= "ks_";
	public static String				DATABASE_MYSQL_HOST		= "localhost";
	public static int					DATABASE_MYSQL_PORT		= 3306;
	public static String				DATABASE_MYSQL_USER		= "root";
	public static String				DATABASE_MYSQL_PASS		= "";
	public static String				DATABASE_MYSQL_NAME		= "minecraft";
	public static String				DATABASE_SQLITE_NAME		= "minecraft.db";

	public static String				TAG_MODE						= "tagapi";
	public static String				TAG_FORMAT					= "&8[{COLOR}{TAG}&8] {COLOR}";
	public static String				TAG_COLOR_NOGUILD			= "&7";
	public static String				TAG_COLOR_FRIEND			= "&a";
	public static String				TAG_COLOR_FRIENDPVP		= "&9";
	public static String				TAG_COLOR_ENEMY			= "&c";
	public static String				TAG_COLOR_ALLIANCE		= "&6";

	public static boolean			UPTAKE_ENABLED				= false;
	public static int					UPTAKE_LIVES_AMOUNT		= 3;
	public static int					UPTAKE_LIVES_TIME			= 24;

	public static boolean			TREASURE_ENABLED			= false;
	public static String				TREASURE_TITLE				= "Skarbiec gildii:";
	public static int					TREASURE_ROWS				= 6;

	// public static boolean TNT_ENABLED = false;
	public static boolean			TNT_OFF_ENABLED			= false;
	public static List<Integer>	TNT_OFF_HOURS				= Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
	public static boolean			TNT_CANTBUILD_ENABLED	= false;
	public static int					TNT_CANTBUILD_TIME		= 90;
	public static boolean			TNT_DURABILITY_ENABLED	= false;
	public static List<String>		TNT_DURABILITY_BLOCKS	= Arrays.asList("OBSIDIAN 73.6", "WATER 10.0", "STATIONARY_WATER 10.0");

	public static String				COST_CREATE					= "1:0-10;";
	public static String				COST_JOIN					= "1:0-10;";
	public static String				COST_LEADER					= "1:0-10;";
	public static String				COST_OWNER					= "1:0-10;";
	public static String				COST_ENLARGE				= "1:0-10;";
	public static String				COST_PROLONG				= "1:0-10;";

	public static int					SIZE_START					= 24;
	public static int					SIZE_MAX						= 74;
	public static int					SIZE_ADD						= 1;
	public static int					SIZE_BETWEEN				= 50;

	public static int					TIME_START					= 3;
	public static int					TIME_MAX						= 14;
	public static int					TIME_ADD						= 7;
	public static int					TIME_CHECK					= 3;
	public static int					TIME_TELEPORT				= 10;

	public static void loadConfig() {
		try {
			FileConfiguration c = GuildPlugin.getPlugin().getConfig();

			for (Field f : Config.class.getFields()) {

				if (c.isSet(prefix + f.getName().toLowerCase().replace("_", ".")))
					f.set(null, c.get(prefix + f.getName().toLowerCase().replace("_", ".")));
				// System.out.println(f.getName() + " -> " + f.get(null));

			}
		} catch (Exception e) {
			Logger.exception(e);
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
			Logger.exception(e);
		}
	}

	public static void reloadConfig() {
		GuildPlugin.getPlugin().reloadConfig();
		loadConfig();
		saveConfig();
	}

}
