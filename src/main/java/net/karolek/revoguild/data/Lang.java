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

	private static final String		prefix										= "lang.";
	private static File					file											= new File(GuildPlugin.getPlugin().getDataFolder(), "lang.yml");
	private static FileConfiguration	c												= null;

	public static String					CMD_MAIN_HELP								= "Dostepne komendy: \n/zaloz <tag> <nazwa> - tworzy gildie";
	public static String					CMD_CORRECT_USAGE							= "Prawidlowe uzycie: /g {NAME} {USAGE}!";

	public static String					ERROR_HAVE_GUILD							= "Blad: Masz juz gildie!";
	public static String					ERROR_TAG_AND_NAME_FORMAT				= "Blad: Tag oraz nazwa musza miec odpowiednia dlugosc!";
	public static String					ERROR_TAG_AND_NAME_ALFANUM				= "Blad: Tag oraz nazwa musza byc alfanumeryczne! (AZaz09)";
	public static String					ERROR_GUILD_EXISTS						= "Blad: Istnieje juz taka gildia!";
	public static String					ERROR_NEARBY_IS_GUILD					= "Blad: W poblizu znajduje sie gildia!";
	public static String					ERROR_DONT_HAVE_ITEMS					= "Blad: Nie posiadasz wystarczajacej ilosci przedmiotow!";
	public static String					ERROR_DONT_HAVE_GUILD					= "Blad: Nie posiadasz gildii!";
	public static String					ERROR_DONT_HAVE_INVITE					= "Blad: nie posiadasz zaproszenia do gildii!";
	public static String					ERROR_NOT_OWNER							= "Blad: Nie jestes zalozycielem!";
	public static String					ERROR_NOT_LEADER							= "Blad: Nie jestes liderem!";
	public static String					ERROR_CANT_FIND_PLAYER					= "Blad: Gracz jest offline!";
	public static String					ERROR_CANT_FIND_GUILD					= "Blad: Taka gildia nie istnieje!";
	public static String					ERROR_PLAYER_IS_MEMBER					= "Blad: Gracz jest czlonkiem Twojej gildii!";
	public static String					ERROR_PLAYER_ISNT_MEMBER				= "Blad: Gracz nie jest czlonkiem Twojej gildii!";
	public static String					ERROR_CANT_KICK_LEADER_OR_OWNER		= "Blad: Nie mozesz wyrzucic lidera i zalozyciela gildii!";
	public static String					ERROR_OWNER_CANT_LEAVE_GUILD			= "Blad: Zalozyciel nie moze opuscic gildii!";
	public static String					ERROR_MAX_SIZE								= "Blad: Twoja gildia ma juz maksymalny rozmiar!";
	public static String					ERROR_CANT_SET_HOME_OUTSIDE_CUBOID	= "Blad: Dom gildii musi byc na jej terenie!";
	public static String					ERROR_NOT_YOUR_GUILD						= "Blad: To nie Twoja gildia!";
	public static String					ERROR_EXPLODE_TNT							= "Blad: Przed chwila wybuchlo TNT! Nie mozesz budowac przez 60 sekund!";
	public static String					ERROR_CANT_TAKE_LIFE						= "Blad: Nie mozna teraz zabrac zycia gildii! Musi uplynac minimum 24h od ostatniej takiej akcji!";

	public static String					INFO_CONFIRM_DELETE						= "Potwierdz usuniecie gildii: /g usun!";
	public static String					INFO_INVITE_SEND							= "Zaproszenie zostalo wyslane!";
	public static String					INFO_INVITE_BACK							= "Zaproszenie zostalo cofniete!";
	public static String					INFO_INVITE_CANCEL						= "Zaproszenie do gildii [{TAG}] {NAME} zostalo cofniete!";
	public static String					INFO_INVITE_NEW							= "Zotales zaproszony do gildii [{TAG}] {NAME}! Zaakceptuj zaproszenie: /g dolacz {TAG}!";
	public static String					INFO_JOINED									= "Dolaczyles do gildii!";
	public static String					INFO_LEADER_CHANGED						= "Lider zostal zmieniony!";
	public static String					INFO_OWNER_CHANGED						= "Zalozyciel zostal zmieniony!";
	public static String					INFO_NOW_LEADER							= "Awansowales na lidera gildii!";
	public static String					INFO_NOW_OWNER								= "Awansowales na zalozyciela gildii!";
	public static String					INFO_RESIZED								= "Powiekszono!";
	public static String					INFO_PVP_ON									= "PVP w gildii zostalo wlaczone!";
	public static String					INFO_PVP_OFF								= "PVP w gildii zostalo wylaczone!";
	public static String					INFO_HOME_SET								= "Dom zostal ustawiony!";
	public static String					INFO_MOVE_IN								= "Wkroczyles na teren gildii [{TAG}] {NAME}!";
	public static String					INFO_MOVE_OUT								= "Opusciles teren gildii [{TAG}] {NAME}!";
	public static String					INFO_GUILD									= "Informacje o gildii [{TAG}] {NAME}:";

	public static String					TELEPORT_START								= "Teleport nastapi za {TIME} sekund! Prosze sie nie ruszac!";
	public static String					TELEPORT_END								= "Przeteleportowano!";
	public static String					TELEPORT_ERROR								= "Teleport przerwany!";

	public static String					LIST_HEADER									= "======= Lista wszystkich gildii =======";
	public static String					LIST_ELEMENT								= "{TAG}  --  {NAME}  --  {OWNER}";
	public static String					LIST_FOOTER									= "=======================================";

	public static String					BC_GUILD_CREATED							= "Gildia [{TAG}] {NAME} zostala utworzona przez {OWNER}!";
	public static String					BC_GUILD_DELETED							= "Gildia [{TAG}] {NAME} zostala usunieta przez {OWNER}!";
	public static String					BC_GUILD_JOINED							= "Gracz {PLAYER} dolaczyl do gildii [{TAG}] {NAME}!";
	public static String					BC_GUILD_KICKED							= "Gracz {PLAYER} zostal wyrzucony z gildii [{TAG}] {NAME}!";
	public static String					BC_GUILD_LEAVED							= "Gracz {PLAYER} opuscil gildie [{TAG}] {NAME}!";
	public static String					BC_GUILD_LIFE_TAKEN						= "Gracz [{TAG2}] {PLAYER} zaatakowal gildie [{TAG}] {NAME}!";
	public static String					BC_GUILD_TAKEN								= "Gracz [{TAG2}] {PLAYER} zniszczyl gildie [{TAG}] {NAME}!";

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
				// System.out.println(f.getName() + " -> " + f.get(null));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveLang() {
		try {
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

	public static String parse(String msg, Guild g, Guild g1, OfflinePlayer p) {
		msg = parse(msg, g, g1);
		msg = parse(msg, p);
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
