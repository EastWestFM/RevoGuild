package net.karolek.revoguild.data;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.utils.Util;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;

public class Lang {

	private static final String		prefix	= "lang.";
	private static File					file		= new File(GuildPlugin.getPlugin().getDataFolder(), "lang.yml");
	private static FileConfiguration	c			= null;
	
	public static String	CMD_ONLY_PLAYER;
	public static String	CMD_NO_PERM;
	public static String	CMD_MAIN_HELP;
	public static String	CMD_CORRECT_USAGE;

	public static String	ERROR_HAVE_GUILD;
	public static String	ERROR_TAG_AND_NAME_FORMAT;
	public static String	ERROR_TAG_AND_NAME_ALFANUM;
	public static String	ERROR_GUILD_EXISTS;
	public static String	ERROR_NEARBY_IS_GUILD;
	public static String	ERROR_DONT_HAVE_ITEMS;
	public static String	ERROR_DONT_HAVE_GUILD;
	public static String	ERROR_DONT_HAVE_INVITE;
	public static String	ERROR_NOT_OWNER;
	public static String	ERROR_NOT_LEADER;
	public static String	ERROR_CANT_FIND_PLAYER;
	public static String	ERROR_CANT_FIND_GUILD;
	public static String	ERROR_PLAYER_IS_MEMBER;
	public static String ERROR_PLAYER_ISNT_MEMBER;
	public static String ERROR_CANT_KICK_LEADER_OR_OWNER;
	public static String ERROR_OWNER_CANT_LEAVE_GUILD;
	public static String ERROR_MAX_SIZE;
	public static String ERROR_CANT_SET_HOME_OUTSIDE_CUBOID;
	public static String ERROR_NOT_YOUR_GUILD;
	public static String ERROR_EXPLODE_TNT;

	public static String	INFO_CONFIRM_DELETE;
	public static String	INFO_INVITE_SEND;
	public static String	INFO_INVITE_BACK;
	public static String	INFO_INVITE_CANCEL;
	public static String	INFO_INVITE_NEW;
	public static String	INFO_JOINED;
	public static String INFO_LEADER_CHANGED;
	public static String INFO_OWNER_CHANGED;
	public static String INFO_NOW_LEADER;
	public static String INFO_NOW_OWNER;
	public static String INFO_RESIZED;
	public static String INFO_PVP_ON;
	public static String INFO_PVP_OFF;
	public static String INFO_HOME_SET;
	public static String INFO_MOVE_IN;
	public static String INFO_MOVE_OUT;
	public static String INFO_GUILD;
	
	public static String TELEPORT_START;
	public static String TELEPORT_END;
	public static String TELEPORT_ERROR;
		
	public static String LIST_HEADER;
	public static String LIST_ELEMENT;
	public static String LIST_FOOTER;

	public static String	BC_GUILD_CREATED;
	public static String	BC_GUILD_DELETED;
	public static String	BC_GUILD_JOINED;
	public static String BC_GUILD_KICKED;
	public static String BC_GUILD_LEAVED;

	public static void loadLang() {
		try {

			if (!file.exists()) {
				file.getParentFile().mkdirs();
				InputStream is = GuildPlugin.getPlugin().getResource(file.getName());
				if (is != null)
					Util.copy(is, file);
			}

			c = YamlConfiguration.loadConfiguration(file);

			for (Field f : Lang.class.getFields()) {

				if (c.isSet(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")))
					f.set(null, c.get(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")));
				System.out.println(f.getName() + " -> " + f.get(null));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveLang() {
		try {
			FileConfiguration c = GuildPlugin.getPlugin().getConfig();
			for (Field f : Lang.class.getFields())
				c.set(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."), f.get(null));

			c.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reloadLang() {
		loadLang();
		saveLang();
	}

	public static String parse(String msg, SubCommand sc) {
		msg = msg.replace("{NAME}", sc.getName());
		msg = msg.replace("{USAGE}", sc.getUsage());
		msg = msg.replace("{DESC}", sc.getDesc());
		msg = msg.replace("{PERM}", sc.getPermission());
		return Util.fixColor(msg);
	}
	
	public static String parse(String msg, Guild g) {
		msg = msg.replace("{TAG}", g.getTag());
		msg = msg.replace("{NAME}", g.getName());
		msg = msg.replace("{OWNER}", Bukkit.getOfflinePlayer(g.getOwner()).getName());
		msg = msg.replace("{LEADER}", Bukkit.getOfflinePlayer(g.getLeader()).getName());
		msg = msg.replace("{CREATETIME}", Util.getDate(g.getCreateTime()));
		msg = msg.replace("{EXPIRETIME}", Util.getDate(g.getExpireTime()));
		msg = msg.replace("{SIZE}", Integer.toString(g.getCuboid().getSize()));
		msg = msg.replace("{PVP}", (g.isPvp() ? "tak" : "nie"));
		msg = msg.replace("{MEMBERS}", getMembers(g));
		msg = msg.replace("{ONLINENUM}", Integer.toString(g.getOnlineMembers().size()));
		msg = msg.replace("{MEMBERSNUM}", Integer.toString(g.getMembers().size()));
		return Util.fixColor(msg);
	}

	public static String parse(String msg, Guild g, Guild g1) {
		msg = parse(msg, g);
		msg = msg.replace("{TAG2}", g1.getTag());
		msg = msg.replace("{NAME2}", g1.getName());
		msg = msg.replace("{OWNER2}", Bukkit.getOfflinePlayer(g1.getOwner()).getName());
		msg = msg.replace("{LEADER2}", Bukkit.getOfflinePlayer(g1.getLeader()).getName());
		msg = msg.replace("{CREATETIME2}", Util.getDate(g1.getCreateTime()));
		msg = msg.replace("{EXPIRETIME2}", Util.getDate(g1.getExpireTime()));
		msg = msg.replace("{SIZE2}", Integer.toString(g1.getCuboid().getSize()));
		msg = msg.replace("{PVP2}", (g1.isPvp() ? "tak" : "nie"));
		msg = msg.replace("{MEMBERS2}", getMembers(g1));
		msg = msg.replace("{ONLINENUM2}", Integer.toString(g1.getOnlineMembers().size()));
		msg = msg.replace("{MEMBERSNUM2}", Integer.toString(g1.getMembers().size()));
		return Util.fixColor(msg);
	}

	public static String parse(String msg, OfflinePlayer p) {
		msg = msg.replace("{PLAYER}", p.getName());
		return Util.fixColor(msg);
	}

	public static String parse(String msg, Guild g, OfflinePlayer p) {
		msg = parse(msg, g);
		msg = parse(msg, p);
		return Util.fixColor(msg);
	}

	private static String getMembers(Guild g) {
		String[] members = new String[g.getMembers().size()];

		int i = 0;
		for (UUID u : g.getMembers()) {
			OfflinePlayer op = Bukkit.getOfflinePlayer(u);
			if (op.isOnline()) {
				members[i] = ChatColor.GREEN + op.getName();
			} else {
				members[i] = ChatColor.RED + op.getName();
			}
			i++;
		}
		return StringUtils.join(members, ChatColor.GRAY + ", " + ChatColor.RESET);
	}

}
