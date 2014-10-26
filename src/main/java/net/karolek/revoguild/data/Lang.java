package net.karolek.revoguild.data;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.Util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Lang {

	private static final String		prefix										= "lang.";
	private static File					file											= new File(GuildPlugin.getPlugin().getDataFolder(), "lang.yml");
	private static FileConfiguration	c												= null;

	public static String					CMD_MAIN_HELP								= "&7&m---------&r &2Komendy systemu gildii &7&m---------&r\n  &2/g zaloz <tag> <nazwa> &7- tworzy gildie\n  &2/g przedluz &7- przedlaza waznsoc gildii\n  &2/g sojusz <tag/nazwa> &7- zarzadzanie sojuszami gildii\n  &2/g usun &7- usuwa gildie\n  &2/g dom &7- teleportuje do domu gildii\n  &2/g info <tag/nazwa> &7- wyswietla informacje o gildii\n  &2/g zapros <gracz> &7- zaprasza gracza do gildii\n  &2/g dolacz <tag/nazwa> - &7dolacza do wybranej gildii\n  &2/g wyrzuc <gracz> &7- wyrzuca gracza z gildii \n  &2/g lider <gracz> &7- zmienia lidera gildii\n  &2/g opusc &7- opuszcza gildie\n  &2/g lista &7- wyswietla liste wszystkich gildii\n  &2/g zalozyciel <gracz> &7- zmienia zalozyciela gidldii\n  &2/g pvp &7- zmienia status pvp w gildii\n  &2/g powieksz &7- powieksza teren gildii\n  &2/g ustawdom &7- ustawia dom gildii\n  &2/g skarbiec [dodaj <gracz> | usun <gracz> | lista] &7- otwiera skarbiec gildii [dodaje gracza do skarbca | usuwa gracza ze skarbca | lista graczy uprawniony do skarbca]";
	public static String					CMD_MAIN_ADMIN_HELP						= "Dostepne komendy dla adminsitratora: \n/ga tp <tag/nazwa> - teleportuje do wybranej gildii\n/ga reload - przeladowanie plikow konfiguracyjnych\n/ga usun <tag/nazwa> - usuwa wybrana gildie";
	public static String					CMD_CORRECT_USAGE							= "&2Prawidlowe uzycie: &7/g {NAME} {USAGE}&2!";

	public static String					ERROR_HAVE_GUILD							= "&4Blad: &cMasz juz gildie!";
	public static String					ERROR_TAG_AND_NAME_FORMAT				= "&4Blad: &cTag oraz nazwa musza miec odpowiednia dlugosc!";
	public static String					ERROR_TAG_AND_NAME_ALFANUM				= "&4Blad: &cTag oraz nazwa musza byc alfanumeryczne! (AZaz09)";
	public static String					ERROR_GUILD_EXISTS						= "&4Blad: &cIstnieje juz taka gildia!";
	public static String					ERROR_NEARBY_IS_GUILD					= "&4Blad: &cW poblizu znajduje sie gildia!";
	public static String					ERROR_DONT_HAVE_ITEMS					= "&4Blad: &cNie posiadasz wystarczajacej ilosci przedmiotow!";
	public static String					ERROR_DONT_HAVE_GUILD					= "&4Blad: &cNie posiadasz gildii!";
	public static String					ERROR_DONT_HAVE_INVITE					= "&4Blad: &cnie posiadasz zaproszenia do gildii!";
	public static String					ERROR_NOT_OWNER							= "&4Blad: &cNie jestes zalozycielem!";
	public static String					ERROR_NOT_LEADER							= "&4Blad: &cNie jestes liderem!";
	public static String					ERROR_CANT_FIND_PLAYER					= "&4Blad: &cGracz jest offline!";
	public static String					ERROR_CANT_FIND_GUILD					= "&4Blad: &cTaka gildia nie istnieje!";
	public static String					ERROR_PLAYER_IS_MEMBER					= "&4Blad: &cGracz jest czlonkiem Twojej gildii!";
	public static String					ERROR_PLAYER_ISNT_MEMBER				= "&4Blad: &cGracz nie jest czlonkiem Twojej gildii!";
	public static String					ERROR_CANT_KICK_LEADER_OR_OWNER		= "&4Blad: &cNie mozesz wyrzucic lidera i zalozyciela gildii!";
	public static String					ERROR_OWNER_CANT_LEAVE_GUILD			= "&4Blad: &cZalozyciel nie moze opuscic gildii!";
	public static String					ERROR_MAX_SIZE								= "&4Blad: &cTwoja gildia ma juz maksymalny rozmiar!";
	public static String					ERROR_CANT_SET_HOME_OUTSIDE_CUBOID	= "&4Blad: &cDom gildii musi byc na jej terenie!";
	public static String					ERROR_NOT_YOUR_GUILD						= "&4Blad: &cTo nie Twoja gildia!";
	public static String					ERROR_EXPLODE_TNT							= "&4Blad: &cPrzed chwila wybuchlo TNT! Nie mozesz budowac przez 60 sekund!";
	public static String					ERROR_CANT_TAKE_LIFE						= "&4Blad: &cNie mozna teraz zabrac zycia gildii! Musi uplynac minimum 24h od ostatniej takiej akcji!";
	public static String					ERROR_CANT_OPEN_TREASURE				= "&4Blad: &cNie jestes uprawniony do otwierania skarbca gildii!";
	public static String					ERROR_PLAYER_IS_TREASURE_USER			= "&4Blad: &cGracz jest juz uzytkownikiem skarbca gildii!";
	public static String					ERROR_PLAYER_ISNT_TREASURE_USER		= "Bad: Gracz nie jest uzytkownikiem skarbca gildii!";
	public static String					ERROR_TREASURE_NOT_ENABLED				= "&4Blad: &cSkarbce gildii nie zostaly aktywowane!";
	public static String					ERROR_CANT_ATTACK_PLAYER				= "&4Blad: &cNie mozesz atakowac tego gracza!";
	public static String					ERROR_CANT_ADD_TIME						= "&4Blad: &cWaznosc gildii nie moze byc wieksza niz 14 dni!";
	public static String					ERROR_OWNER_NOT_ONLINE					= "&4Blad: &cZalozyciel gildii nie jest online!";
	public static String					ERROR_CANT_BUILD_NEAR_EGG				= "&4Blad: &cNie mozesz budowac w poblizu smoczego jaja!";
	public static String					ERROR_CANT_USE								= "&4Blad: &cNie mozesz tego uzywac!";

	public static String					INFO_CONFIRM_DELETE						= "&2Potwierdz usuniecie gildii: &7/g usun&2!";
	public static String					INFO_INVITE_SEND							= "&2Zaproszenie zostalo wyslane!";
	public static String					INFO_INVITE_BACK							= "&2Zaproszenie zostalo cofniete!";
	public static String					INFO_INVITE_CANCEL						= "&2Zaproszenie do gildii &7[{TAG}] {NAME}&2 zostalo cofniete!";
	public static String					INFO_INVITE_NEW							= "&2Zotales zaproszony do gildii &7[{TAG}] {NAME}&2! Zaakceptuj zaproszenie: &7/g dolacz {TAG}&2!";
	public static String					INFO_JOINED									= "&2Dolaczyles do gildii!";
	public static String					INFO_LEADER_CHANGED						= "&2Lider zostal zmieniony!";
	public static String					INFO_OWNER_CHANGED						= "&2Zalozyciel zostal zmieniony!";
	public static String					INFO_NOW_LEADER							= "&2Awansowales na lidera gildii!";
	public static String					INFO_NOW_OWNER								= "&2Awansowales na zalozyciela gildii!";
	public static String					INFO_RESIZED								= "&2Powiekszono!";
	public static String					INFO_PVP_ON									= "&2PVP w gildii zostalo wlaczone!";
	public static String					INFO_PVP_OFF								= "&2PVP w gildii zostalo wylaczone!";
	public static String					INFO_HOME_SET								= "&2Dom zostal ustawiony!";
	public static String					INFO_MOVE_IN								= "&2Wkroczyles na teren gildii &7[{TAG}] {NAME}&2!";
	public static String					INFO_MOVE_OUT								= "&2Opusciles teren gildii &7[{TAG}] {NAME}&2!";
	public static String					INFO_GUILD									= "&7&m---------&r &7Gildia &2[{TAG}] {NAME} &7&m---------&r\n  &2Zalozyciel: &7{OWNER}\n  &2Lider: &7{LEADER}\n  &2Utworzona: &7{CREATETIME}\n  &2Wygasa: &7{EXPIRETIME}\n  &2Ostatni atak: &7{LASTTAKENLIFETIME}\n  &2Zgony: &7{DEATHS}\n  &2Zabicia: &7{KILLS}\n  &2Punkty: &7{POINTS}\n  &2Pvp: &7{PVP}\n  &2Zycia: &7{LIVES}\n  &2Rozmiar: &7{SIZE}x{SIZE}\n  &2Czlonkow: &7{MEMBERSNUM}, online: {ONLINENUM}\n  &2Czlonkowie: &7{MEMBERS}\n";
	public static String					INFO_PLAYER									= "&7&m---------&r &7Gracz &2{NAME} &7&m---------&r\n  &2Punkty: &7{POINTS}\n  &2Zabicia: &7{KILLS}\n  &2Zgony: &7{DEATHS}";
	public static String					INFO_TREASURE_OPENED						= "&2Otwarto skarbiec gildii!";
	public static String					INFO_TREASURE_USER_ADD					= "&2Gracz &7{PLAYER} &2jest od teraz uzytkownikiem skarbca gildii!";
	public static String					INFO_TREASURE_USER_ADD_INFO			= "&2Gracz &7{PLAYER} &2nadal Ci uprawnienia do skarbca gildii!";
	public static String					INFO_TREASURE_USER_REMOVE				= "&2Gracz &7{PLAYER} &2nie jest od teraz uzytkownikiem skarbca gildii!";
	public static String					INFO_TREASURE_USER_REMOVE_INFO		= "&2Gracz &7{PLAYER} &2odebral Ci uprawnienia do skarbca gildii!";
	public static String					INFO_TREASURE_USERS						= "&2Lista uzytkownikow skarbca: {USERS}";
	public static String					INFO_PROLONGED_VALIDITY					= "&2Przedluzo waznosc gildii!";
	public static String					INFO_ALLY_NEW								= "&2Twoja gildia zostala zaproszona do sojuszu przez gildie  &7[{TAG}] {NAME}&2! Zaakceptuj uzywajac &7/g sojusz {TAG}&2!";
	public static String					INFO_ALLY_SEND								= "&2Zaprosiles gildie &7[{TAG}] {NAME}&2 do sojuszu!";
	public static String					INFO_RELOADED								= "&2Przeladowano plik config.yml oraz lang.yml!";

	public static String					TELEPORT_START								= "&2Teleport nastapi za &7{TIME} &2sekund! Prosze sie nie ruszac!";
	public static String					TELEPORT_END								= "&2Przeteleportowano!";
	public static String					TELEPORT_ERROR								= "&2Teleport przerwany!";

	public static String					LIST_GUILD_HEADER							= "&7&m--------&r &2Lista wszystkich gildii &7&m--------&r \n  &7&otag - nazwa - zalozyciel";
	public static String					LIST_GUILD_ELEMENT						= "  &7{TAG} -  {NAME} -  {OWNER}";
	public static String					LIST_GUILD_FOOTER							= "&7&m----------------------------------";

	public static String					LIST_RANKING_HEADER						= "&7&m--------&r &2Ranking graczy (TOP 10)&7&m--------&r ";
	public static String					LIST_RANKING_ELEMENT						= "  &7{POS}. {NAME} - {POINTS}; {KILLS}:{DEATHS}";
	public static String					LIST_RANKING_FOOTER						= "&7&m----------------------------------";

	public static String					ADMIN_BC_GUILD_DELETED					= "&4Gildia &7[{TAG}] {NAME}&4 zostala usunieta przez administratora &7{PLAYER}&4!";

	public static String					BC_GUILD_CREATED							= "&2Gildia &7[{TAG}] {NAME}&2 zostala utworzona przez &7{OWNER}&2!";
	public static String					BC_GUILD_DELETED							= "Gildia &7[{TAG}] {NAME}&2 zostala usunieta przez &7{OWNER}!";
	public static String					BC_GUILD_JOINED							= "&2Gracz &7{PLAYER} &2dolaczyl do gildii &7[{TAG}] {NAME}&2!";
	public static String					BC_GUILD_KICKED							= "&2Gracz &7{PLAYER} &2zostal wyrzucony z gildii &7[{TAG}] {NAME}&2!";
	public static String					BC_GUILD_LEAVED							= "&2Gracz &7{PLAYER} &2opuscil gildie &7[{TAG}] {NAME}&2!";
	public static String					BC_GUILD_LIFE_TAKEN						= "&2Gracz &7[{TAG2}] &2{PLAYER} zaatakowal gildie &7[{TAG}] {NAME}&2!";
	public static String					BC_GUILD_TAKEN								= "&2Gracz &7[{TAG2}] &2{PLAYER} zniszczyl gildie &7[{TAG}] {NAME}&2!";
	public static String					BC_GUILD_EXPIRED							= "&2Gildia &7[{TAG}] {NAME}&2 stracila swoja waznosc! Jej stare koorydynaty: &7[x: {X}; z: {Z}]&2!";
	public static String					BC_GUILD_ALLIANCE_CREATED				= "&2Gildia &7[{TAG}] {NAME}&2 zawarla sojusz z gildia &7[{TAG2}] {NAME2}&2!";
	public static String					BC_GUILD_ALLIANCE_DELETED				= "&2Gildia &7[{TAG}] {NAME}&2 zerwala sojusz z gildia &7[{TAG2}] {NAME2}&2!";

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
			Logger.exception(e);
		}
	}

	public static void saveLang() {
		try {
			for (Field f : Lang.class.getFields())
				c.set(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."), f.get(null));

			c.save(file);
		} catch (Exception e) {
			Logger.exception(e);
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

	public static String parse(String msg, User u) {
		msg = msg.replace("{NAME}", u.getName());
		msg = msg.replace("{POINTS}", Integer.toString(u.getPoints()));
		msg = msg.replace("{KILLS}", Integer.toString(u.getKills()));
		msg = msg.replace("{DEATHS}", Integer.toString(u.getDeaths()));
		msg = msg.replace("{TIMEPLAY}", Util.secondsToString(u.getTimePlay()));
		return Util.fixColor(msg);
	}

	public static String parse(String msg, Guild g) {
		msg = msg.replace("{TAG}", g.getTag());
		msg = msg.replace("{NAME}", g.getName());
		msg = msg.replace("{OWNER}", g.getOwner().getOfflinePlayer().getName());
		msg = msg.replace("{LEADER}", g.getLeader().getOfflinePlayer().getName());
		msg = msg.replace("{CREATETIME}", Util.getDate(g.getCreateTime()));
		msg = msg.replace("{EXPIRETIME}", Util.getDate(g.getExpireTime()));
		msg = msg.replace("{LASTTAKENLIFETIME}", Util.getDate(g.getLastTakenLifeTime()));
		msg = msg.replace("{SIZE}", Integer.toString(g.getCuboid().getSize()));
		msg = msg.replace("{PVP}", (g.isPvp() ? "tak" : "nie"));
		msg = msg.replace("{MEMBERS}", getMembers(g));
		msg = msg.replace("{ONLINENUM}", Integer.toString(g.getOnlineMembers().size()));
		msg = msg.replace("{MEMBERSNUM}", Integer.toString(g.getMembers().size()));
		msg = msg.replace("{POINTS}", Integer.toString(g.getPoints()));
		msg = msg.replace("{KILLS}", Integer.toString(g.getKills()));
		msg = msg.replace("{DEATHS}", Integer.toString(g.getDeaths()));
		msg = msg.replace("{LIVES}", Integer.toString(g.getLives()));
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
		msg = msg.replace("{OWNER2}", g1.getOwner().getOfflinePlayer().getName());
		msg = msg.replace("{LEADER2}", g1.getLeader().getOfflinePlayer().getName());
		msg = msg.replace("{CREATETIME2}", Util.getDate(g1.getCreateTime()));
		msg = msg.replace("{EXPIRETIME2}", Util.getDate(g1.getExpireTime()));
		msg = msg.replace("{LASTTAKENLIFETIME2}", Util.getDate(g1.getLastTakenLifeTime()));
		msg = msg.replace("{SIZE2}", Integer.toString(g1.getCuboid().getSize()));
		msg = msg.replace("{PVP2}", (g1.isPvp() ? "tak" : "nie"));
		msg = msg.replace("{MEMBERS2}", getMembers(g1));
		msg = msg.replace("{ONLINENUM2}", Integer.toString(g1.getOnlineMembers().size()));
		msg = msg.replace("{MEMBERSNUM2}", Integer.toString(g1.getMembers().size()));
		msg = msg.replace("{POINTS2}", Integer.toString(g1.getPoints()));
		msg = msg.replace("{KILLS2}", Integer.toString(g1.getKills()));
		msg = msg.replace("{DEATHS2}", Integer.toString(g1.getDeaths()));
		msg = msg.replace("{LIVES2}", Integer.toString(g1.getLives()));
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

	public static String getMembers(Guild g) {
		String[] members = new String[g.getMembers().size()];

		int i = 0;
		for (User u : g.getMembers()) {
			OfflinePlayer op = u.getOfflinePlayer();
			if (op.isOnline()) {
				members[i] = ChatColor.GREEN + op.getName();
			} else {
				members[i] = ChatColor.RED + op.getName();
			}
			i++;
		}
		return StringUtils.join(members, ChatColor.GRAY + ", " + ChatColor.RESET);
	}

	public static String getTreasureUsers(Guild g) {
		String[] members = new String[g.getMembers().size()];

		int i = 0;
		for (User u : g.getTreasureUsers()) {
			OfflinePlayer op = u.getOfflinePlayer();
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
