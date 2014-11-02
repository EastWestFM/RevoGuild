package net.karolek.revoguild.data;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.utils.Logger;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	private static final String	prefix										= "config.";

	public static boolean			ENABLED										= false;
	public static boolean			USEUUID										= true;

	public static String				DATABASE_MODE								= "sqlite";
	public static String				DATABASE_TABLEPREFIX						= "ks_";
	public static String				DATABASE_MYSQL_HOST						= "localhost";
	public static int					DATABASE_MYSQL_PORT						= 3306;
	public static String				DATABASE_MYSQL_USER						= "root";
	public static String				DATABASE_MYSQL_PASS						= "";
	public static String				DATABASE_MYSQL_NAME						= "minecraft";
	public static String				DATABASE_SQLITE_NAME						= "minecraft.db";

	public static String				TAG_MODE										= "tagapi";
	public static String				TAG_FORMAT									= "&8[{COLOR}{TAG}&8] {COLOR}";
	public static String				TAG_COLOR_NOGUILD							= "&7";
	public static String				TAG_COLOR_FRIEND							= "&a";
	public static String				TAG_COLOR_FRIENDPVP						= "&9";
	public static String				TAG_COLOR_ENEMY							= "&c";
	public static String				TAG_COLOR_ALLIANCE						= "&6";

	public static String				CHAT_FORMAT_TAG							= "&8[&2{TAG}&8]&r ";
	public static String				CHAT_FORMAT_RANK							= "&8[&2{RANK}&8]&r ";
	public static String				CHAT_FORMAT_TAGDEATHMSG					= "&7[&2{TAG}&7]&r ";
	public static boolean			CHAT_LOCAL_ENABLED						= false;
	public static String				CHAT_LOCAL_CHAR							= "!";
	public static String				CHAT_LOCAL_FORMAT_GUILD					= "&2{PLAYER} -> Gildia: &a{MESSAGE}";
	public static String				CHAT_LOCAL_FORMAT_ALLIANCE				= "&6{TAG} {PLAYER} -> Sojusz: &e{MESSAGE}";

	public static int					RANKING_STARTPOINTS						= 1000;
	public static String				RANKING_DEATHMESSAGE						= "&2Gracz {PGUILD} &7{PLAYER} ({LOSEPOINTS}) &2zostal zabity przez {KGUILD} &7{KILLER} ({WINPOINTS})&2!";

	public static boolean			ESCAPE_ENABLED								= true;
	public static int					ESCAPE_TIME									= 10;
	public static int					ESCAPE_POINTSREMOVE						= 50;
	public static String				ESCAPE_BROADCAST							= "&2Gracz {PGUILD} &7{PLAYER} &7(-50) &2wylogowal sie podczas walki!";
	public static boolean			ESCAPE_DISABLEDCMD_ENABLED				= false;
	public static List<String>		ESCAPE_DISABLEDCMD_COMMANDS			= Arrays.asList("g dom", "dom", "spawn");
	public static boolean			ESCAPE_DISABLEDCMD_NOTIFY_ENABLED	= false;
	public static String				ESCAPE_DISABLEDCMD_NOTIFY_MESSAGE	= "&4Blad: &cNie mozesz uzywac tej komendy podczas walki!";
	public static String				ALGORITHM_RANKING_WIN					= "(300 + (({KILLER_POINTS} - {PLAYER_POINTS}) * (-0.2)))";
	public static String				ALGORITHM_RANKING_LOSE					= "Math.abs({WIN_POINTS}/2)";
	public static String				ALGORITHM_GUILD_POINTS					= "{MEMBERS_POINTS} - ({MEMBERS_NUM}*1000)";
	public static String				ALGORITHM_ENLARGE							= "({CUBOID_SIZE} - 24)/5 +1";

	public static boolean			ACTIONS_BLOCK_BREAK						= false;
	public static boolean			ACTIONS_BLOCK_PLACE						= false;
	public static boolean			ACTIONS_BUCKET_EMPTY						= false;
	public static boolean			ACTIONS_BUCKET_FILL						= false;
	public static List<Integer>	ACTIONS_PROTECTEDID						= Arrays.asList(54);

	public static boolean			UPTAKE_ENABLED								= false;
	public static int					UPTAKE_LIVES_START						= 3;
	public static int					UPTAKE_LIVES_MAX							= 6;
	public static int					UPTAKE_LIVES_TIME							= 24;

	public static boolean			TREASURE_ENABLED							= false;
	public static boolean			TREASURE_OPENONLYONGUILD				= true;
	public static String				TREASURE_TITLE								= "Skarbiec gildii:";
	public static int					TREASURE_ROWS								= 6;

	public static boolean			TNT_OFF_ENABLED							= false;
	public static List<Integer>	TNT_OFF_HOURS								= Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
	public static boolean			TNT_PROTECTION_ENABLED					= false;
	public static int					TNT_PROTECTION_TIME						= 24;
	public static boolean			TNT_CANTBUILD_ENABLED					= false;
	public static int					TNT_CANTBUILD_TIME						= 90;
	public static boolean			TNT_DURABILITY_ENABLED					= false;
	public static List<String>		TNT_DURABILITY_BLOCKS					= Arrays.asList("OBSIDIAN 73.6", "WATER 10.0", "STATIONARY_WATER 10.0");

	public static String				COST_CREATE_NORMAL						= "1:0-10;";
	public static String				COST_JOIN_NORMAL							= "1:0-10;";
	public static String				COST_LEADER_NORMAL						= "1:0-10;";
	public static String				COST_OWNER_NORMAL							= "1:0-10;";
	public static String				COST_ENLARGE_NORMAL						= "1:0-10;";
	public static String				COST_PROLONG_NORMAL						= "1:0-10;";
	public static String				COST_CREATE_VIP							= "1:0-5;";
	public static String				COST_JOIN_VIP								= "1:0-5;";
	public static String				COST_LEADER_VIP							= "1:0-5;";
	public static String				COST_OWNER_VIP								= "1:0-5;";
	public static String				COST_ENLARGE_VIP							= "1:0-5;";
	public static String				COST_PROLONG_VIP							= "1:0-5;";

	public static String				CUBOID_WORLD								= "world";
	public static int					CUBOID_SIZE_START							= 24;
	public static int					CUBOID_SIZE_MAX							= 74;
	public static int					CUBOID_SIZE_ADD							= 1;
	public static int					CUBOID_SIZE_BETWEEN						= 50;
	public static int					CUBOID_SPAWN_DISTANCE					= 400;

	public static int					TIME_START									= 3;
	public static int					TIME_MAX										= 14;
	public static int					TIME_ADD										= 7;
	public static int					TIME_CHECK									= 3;
	public static int					TIME_TELEPORT								= 10;

	public static boolean			TABLIST_ENABLED							= false;
	public static int					TABLIST_REFRESH_INTERVAL				= 1;

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
