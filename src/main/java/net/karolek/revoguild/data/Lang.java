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

	private static final String		prefix												= "lang.";
	private static File					file													= new File(GuildPlugin.getPlugin().getDataFolder(), "lang.yml");
	private static FileConfiguration	c														= null;

	public static String					CMD_MAIN_HELP										= "&7&m----------&r &6RevoGUILD: komendy uzytkownika &7&m----------&r\n  &6/g sojusz <tag/nazwa> &7- zarzadzanie sojuszami gildii\n  &6/g zaloz <tag> <nazwa> &7- tworzenie gildii\n  &6/g usun &7- usuwanie gildii\n  &6/g powieksz &7- powiekszanie gildii\n  &6/g dom &7- teleport do domu gildii\n  &6/g info <tag/nazwa> &7- podstawowe informacje o gildii\n  &6/g zapros <gracz> &7- zapraszanie graczy do gildii\n  &6/g dolacz <tag/nazwa> &7- dolaczanie do gildii\n  &6/g wyrzuc <gracz> &7- wyrzucanie graczy z gildii\n  &6/g lider <gracz> &7- zmiana lidera gildii\n  &6/g opusc &7- opuszczanie gildii\n  &6/g lista &7- lista wszystkich gildii\n  &6/g zalozyciel <gracz> &7- zmiana zalozyciela gildii\n  &6/g przedluz &7- przedluzanie waznosci gildii\n  &6/g pvp &7- zmiana statusu pvp w gildii\n  &6/g ustawdom &7- ustawianie domu gildii\n  &6/g skarbiec [dodaj <gracz> | usun <gracz> | lista] &7- zarzadzanie skarbcem gildii\n&7&m----------------------------------------------------";
	public static String					CMD_MAIN_ADMIN_HELP								= "&7&m-------&r &6RevoGUILD: komendy administratora &7&m-------&r\n  &6/admin g &7- administracja gildiami\n  &6/admin r &7- administracja rankingiem\n&7&m----------------------------------------------------";
	public static String					CMD_MAIN_ADMIN_GUILD_HELP						= "&7&m-------&r &6RevoGUILD: komendy administratora gildii &7&m-------&r\n  &6/ga tp <tag/nazwa> &7- teleport do gildii\n  &6/ga usun <tag/nazwa> &7- usuwanie gildii\n  &6/ga ban <tag/nazwa> <czas> <powod> &7- banowanie gildii\n  &6/ga unban <tag/nazwa> &7- odbanowywanie gildii\n  &6/ga setsize <tag/nazwa> <rozmiar> &7- zmiana rozmiaru cuboida gildii\n  &6/ga setcuboid <tag/nazwa> &7- zmiana cuboida gildii\n  &6/ga wyrzuc <tag/nazwa> <gracz> &7- wyrzucanie gracza z gildii\n  &6/ga reload &7- przeladowanie plikow konfiguracyjnych\n&7&m----------------------------------------------------";
	public static String					CMD_MAIN_ADMIN_RANKING_HELP					= "&7&m-------&r &6RevoGUILD: komendy administratora rankingu &7&m-------&r\n  &6/ra reset <gracz> &7- reset rankingu gracza\n  &6/ga set <gracz> <kills|deaths|points> <warotsc> &7- ustawianie wartosci gracza\n&7&m----------------------------------------------------";
	public static String					CMD_CORRECT_USAGE									= "&6Prawidlowe uzycie: &7/g {NAME} {USAGE}&6!";

	public static String					ERROR_HAVE_GUILD									= "&4Blad: &cMasz juz gildie!";
	public static String					ERROR_TAG_AND_NAME_FORMAT						= "&4Blad: &cTag oraz nazwa musza miec odpowiednia dlugosc!";
	public static String					ERROR_TAG_AND_NAME_ALFANUM						= "&4Blad: &cTag oraz nazwa musza byc alfanumeryczne! (AZaz09)";
	public static String					ERROR_BAD_INTEGER									= "&4Blad: &cPodana wartosc nie jest liczba!";
	public static String					ERROR_GUILD_EXISTS								= "&4Blad: &cIstnieje juz taka gildia!";
	public static String					ERROR_NEARBY_IS_GUILD							= "&4Blad: &cW poblizu znajduje sie gildia lub spawn!";
	public static String					ERROR_DONT_HAVE_ITEMS							= "&4Blad: &cNie posiadasz wystarczajacej ilosci przedmiotow!";
	public static String					ERROR_DONT_HAVE_GUILD							= "&4Blad: &cNie posiadasz gildii!";
	public static String					ERROR_DONT_HAVE_INVITE							= "&4Blad: &cnie posiadasz zaproszenia do gildii!";
	public static String					ERROR_NOT_OWNER									= "&4Blad: &cNie jestes zalozycielem!";
	public static String					ERROR_NOT_LEADER									= "&4Blad: &cNie jestes liderem!";
	public static String					ERROR_CANT_FIND_PLAYER							= "&4Blad: &cGracz jest offline!";
	public static String					ERROR_CANT_FIND_GUILD							= "&4Blad: &cTaka gildia nie istnieje!";
	public static String					ERROR_PLAYER_IS_MEMBER							= "&4Blad: &cGracz jest czlonkiem Twojej gildii!";
	public static String					ERROR_PLAYER_ISNT_MEMBER						= "&4Blad: &cGracz nie jest czlonkiem Twojej gildii!";
	public static String					ERROR_CANT_KICK_LEADER_OR_OWNER				= "&4Blad: &cNie mozesz wyrzucic lidera i zalozyciela gildii!";
	public static String					ERROR_OWNER_CANT_LEAVE_GUILD					= "&4Blad: &cZalozyciel nie moze opuscic gildii!";
	public static String					ERROR_MAX_SIZE										= "&4Blad: &cTwoja gildia ma juz maksymalny rozmiar!";
	public static String					ERROR_CANT_SET_HOME_OUTSIDE_CUBOID			= "&4Blad: &cDom gildii musi byc na jej terenie!";
	public static String					ERROR_NOT_YOUR_GUILD								= "&4Blad: &cTo nie Twoja gildia!";
	public static String					ERROR_EXPLODE_TNT									= "&4Blad: &cPrzed chwila wybuchlo TNT! Nie mozesz budowac przez 60 sekund!";
	public static String					ERROR_CANT_TAKE_LIFE								= "&4Blad: &cNie mozna teraz zabrac zycia gildii! Musi uplynac minimum 24h od ostatniej takiej akcji!";
	public static String					ERROR_CANT_OPEN_TREASURE						= "&4Blad: &cNie jestes uprawniony do otwierania skarbca gildii!";
	public static String					ERROR_PLAYER_IS_TREASURE_USER					= "&4Blad: &cGracz jest juz uzytkownikiem skarbca gildii!";
	public static String					ERROR_PLAYER_ISNT_TREASURE_USER				= "&4Blad: Gracz nie jest uzytkownikiem skarbca gildii!";
	public static String					ERROR_TREASURE_NOT_ENABLED						= "&4Blad: &cSkarbce gildii nie zostaly aktywowane!";
	public static String					ERROR_CANT_ATTACK_PLAYER						= "&4Blad: &cNie mozesz atakowac tego gracza!";
	public static String					ERROR_CANT_ADD_TIME								= "&4Blad: &cWaznosc gildii nie moze byc wieksza niz 14 dni!";
	public static String					ERROR_OWNER_NOT_ONLINE							= "&4Blad: &cZalozyciel gildii nie jest online!";
	public static String					ERROR_CANT_BUILD_NEAR_EGG						= "&4Blad: &cNie mozesz budowac w poblizu smoczego jaja!";
	public static String					ERROR_CANT_USE										= "&4Blad: &cNie mozesz tego uzywac!";
	public static String					ERROR_CANT_OPEN_TREASURE_OUTSIDE_CUBOID	= "&4Blad: &cSkarbiec gildii mozna otwierac tylko na terenie gildii!";
	public static String					ERROR_CANT_SET_CUBOID							= "&4Blad: &cNie mozna przeniesc cuboida gildii poniewaz w poblizu znajduje sie inna gildia/spawn!";
	public static String					ERROR_CANT_FIND_USER								= "&4Blad: Uzytkownik nie istnieje w bazie danych!";
	public static String					ERROR_GUILD_HAVE_BAN								= "&4Blad: &cGildia ma juz bana!";
	public static String					ERROR_GUILD_DONT_HAVE_BAN						= "&4Blad: &cGildia nie ma bana!";

	public static String					INFO_CONFIRM_DELETE								= "&6Potwierdz usuniecie gildii: &7/g usun&6!";
	public static String					INFO_INVITE_SEND									= "&6Zaproszenie zostalo wyslane!";
	public static String					INFO_INVITE_BACK									= "&6Zaproszenie zostalo cofniete!";
	public static String					INFO_INVITE_CANCEL								= "&6Zaproszenie do gildii &7[{TAG}] {NAME}&6 zostalo cofniete!";
	public static String					INFO_INVITE_NEW									= "&6Zotales zaproszony do gildii &7[{TAG}] {NAME}&6! Zaakceptuj zaproszenie: &7/g dolacz {TAG}&6!";
	public static String					INFO_JOINED											= "&6Dolaczyles do gildii!";
	public static String					INFO_LEADER_CHANGED								= "&6Lider zostal zmieniony!";
	public static String					INFO_OWNER_CHANGED								= "&6Zalozyciel zostal zmieniony!";
	public static String					INFO_NOW_LEADER									= "&6Awansowales na lidera gildii!";
	public static String					INFO_NOW_OWNER										= "&6Awansowales na zalozyciela gildii!";
	public static String					INFO_RESIZED										= "&6Powiekszono!";
	public static String					INFO_PVP_ON											= "&6PVP w gildii zostalo wlaczone!";
	public static String					INFO_PVP_OFF										= "&6PVP w gildii zostalo wylaczone!";
	public static String					INFO_HOME_SET										= "&6Dom zostal ustawiony!";
	public static String					INFO_MOVE_IN										= "&6Wkroczyles na teren gildii &7[{TAG}] {NAME}&6!";
	public static String					INFO_MOVE_OUT										= "&6Opusciles teren gildii &7[{TAG}] {NAME}&6!";
	public static String					INFO_GUILD											= "&7&m---------&r &7Gildia &6[{TAG}] {NAME} &7&m---------&r\n  &6Zalozyciel: &7{OWNER}\n  &6Lider: &7{LEADER}\n  &6Utworzona: &7{CREATETIME}\n  &6Wygasa: &7{EXPIRETIME}\n  &6Ostatni atak: &7{LASTTAKENLIFETIME}\n  &6Zgony: &7{DEATHS}\n  &6Zabicia: &7{KILLS}\n  &6Punkty: &7{POINTS}\n  &6Pvp: &7{PVP}\n  &6Zycia: &7{LIVES}\n  &6Rozmiar: &7{SIZE}x{SIZE}\n  &6Czlonkow: &7{MEMBERSNUM}, online: {ONLINENUM}\n  &6Czlonkowie: &7{MEMBERS}\n";
	public static String					INFO_PLAYER											= "&7&m---------&r &7Gracz &6{NAME} &7&m---------&r\n  &6Punkty: &7{POINTS}\n  &6Zabicia: &7{KILLS}\n  &6Zgony: &7{DEATHS}";
	public static String					INFO_TREASURE_OPENED								= "&6Otwarto skarbiec gildii!";
	public static String					INFO_TREASURE_USER_ADD							= "&6Gracz &7{PLAYER} &6jest od teraz uzytkownikiem skarbca gildii!";
	public static String					INFO_TREASURE_USER_ADD_INFO					= "&6Gracz &7{PLAYER} &6nadal Ci uprawnienia do skarbca gildii!";
	public static String					INFO_TREASURE_USER_REMOVE						= "&6Gracz &7{PLAYER} &6nie jest od teraz uzytkownikiem skarbca gildii!";
	public static String					INFO_TREASURE_USER_REMOVE_INFO				= "&6Gracz &7{PLAYER} &6odebral Ci uprawnienia do skarbca gildii!";
	public static String					INFO_TREASURE_USERS								= "&6Lista uzytkownikow skarbca: {USERS}";
	public static String					INFO_PROLONGED_VALIDITY							= "&6Przedluzo waznosc gildii!";
	public static String					INFO_ALLY_NEW										= "&6Twoja gildia zostala zaproszona do sojuszu przez gildie  &7[{TAG}] {NAME}&6! Zaakceptuj uzywajac &7/g sojusz {TAG}&6!";
	public static String					INFO_ALLY_SEND										= "&6Zaprosiles gildie &7[{TAG}] {NAME}&6 do sojuszu!";
	public static String					INFO_RELOADED										= "&6Przeladowano plik config.yml oraz lang.yml!";
	public static String					INFO_CUBOID_SET									= "&6Zmieniono teren gildii!";
	public static String					INFO_RESETED										= "&6Zresetowano ranking gracza!";
	public static String					INFO_SETTED											= "&6Zmieniono wartosci gracza!";
	public static String					INFO_USER_KICKED									= "&6Wyrzucono gracza z gildii!";
	public static String					INFO_SIZE_CHANGED									= "&6Zmieniono rozmiar gildii!";
	public static String					INFO_FIGHT_START									= "&cJestes w trakcie walki! Nie mozesz sie wylogowac przez 20 sekund!";
	public static String					INFO_FIGHT_END										= "&aSkonczyles walczyc! Mozesz sie spokojnie wylogowac! ;)";

	public static String					TELEPORT_START										= "&6Teleport nastapi za &7{TIME} &6sekund! Prosze sie nie ruszac!";
	public static String					TELEPORT_END										= "&6Przeteleportowano!";
	public static String					TELEPORT_ERROR										= "&6Teleport przerwany!";

	public static String					LIST_GUILD_HEADER									= "&7&m--------&r &6Lista wszystkich gildii &7&m--------&r \n  &7&otag - nazwa - zalozyciel";
	public static String					LIST_GUILD_ELEMENT								= "  &7{TAG} -  {NAME} -  {OWNER}";
	public static String					LIST_GUILD_FOOTER									= "&7&m----------------------------------";

	public static String					LIST_RANKING_HEADER								= "&7&m--------&r &6Ranking graczy (TOP 10)&7&m--------&r ";
	public static String					LIST_RANKING_ELEMENT								= "  &7{POS}. {NAME} - {POINTS}; {KILLS}:{DEATHS}";
	public static String					LIST_RANKING_FOOTER								= "&7&m----------------------------------";

	public static String					BAN_KICKED											= "&cTwoja gildia zostala zbanowana przez {BANADMIN}!\nPowod: {BANREASON}\nWygasa: {BANTIME}";

	public static String					ADMIN_BC_GUILD_DELETED							= "&4Gildia &7[{TAG}] {NAME}&4 zostala usunieta przez administratora &7{PLAYER}&4!";
	public static String					ADMIN_BC_GUILD_BANNED							= "&4Gildia &7[{TAG}] {NAME}&4 zostala zbanowana przez administratora &7{PLAYER}&4 z powodu: &7{BANREASON}!";
	public static String					ADMIN_BC_GUILD_UNBANNED							= "&4Gildia &7[{TAG}] {NAME}&4 zostala odbanowana przez administratora &7{PLAYER}&4!";

	public static String					BC_GUILD_CREATED									= "&6Gildia &7[{TAG}] {NAME}&6 zostala utworzona przez &7{OWNER}&6!";
	public static String					BC_GUILD_DELETED									= "&6Gildia &7[{TAG}] {NAME}&6 zostala usunieta przez &7{OWNER}!";
	public static String					BC_GUILD_JOINED									= "&6Gracz &7{PLAYER} &6dolaczyl do gildii &7[{TAG}] {NAME}&6!";
	public static String					BC_GUILD_KICKED									= "&6Gracz &7{PLAYER} &6zostal wyrzucony z gildii &7[{TAG}] {NAME}&6!";
	public static String					BC_GUILD_LEAVED									= "&6Gracz &7{PLAYER} &6opuscil gildie &7[{TAG}] {NAME}&6!";
	public static String					BC_GUILD_LIFE_TAKEN								= "&6Gracz &7[{TAG2}] &6{PLAYER} zaatakowal gildie &7[{TAG}] {NAME}&6!";
	public static String					BC_GUILD_TAKEN										= "&6Gracz &7[{TAG2}] &6{PLAYER} zniszczyl gildie &7[{TAG}] {NAME}&6!";
	public static String					BC_GUILD_EXPIRED									= "&6Gildia &7[{TAG}] {NAME}&6 stracila swoja waznosc! Jej stare koorydynaty: &7[x: {X}; z: {Z}]&6!";
	public static String					BC_GUILD_ALLIANCE_CREATED						= "&6Gildia &7[{TAG}] {NAME}&6 zawarla sojusz z gildia &7[{TAG2}] {NAME2}&6!";
	public static String					BC_GUILD_ALLIANCE_DELETED						= "&6Gildia &7[{TAG}] {NAME}&6 zerwala sojusz z gildia &7[{TAG2}] {NAME2}&6!";

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
		msg = msg.replace("{SIZE}", Integer.toString(g.getCuboid().getSize() * 2 + 1));
		msg = msg.replace("{PVP}", (g.isPvp() ? "tak" : "nie"));
		msg = msg.replace("{MEMBERS}", getMembers(g));
		msg = msg.replace("{ONLINENUM}", Integer.toString(g.getOnlineMembers().size()));
		msg = msg.replace("{MEMBERSNUM}", Integer.toString(g.getMembers().size()));
		msg = msg.replace("{POINTS}", Integer.toString(g.getPoints()));
		msg = msg.replace("{KILLS}", Integer.toString(g.getKills()));
		msg = msg.replace("{DEATHS}", Integer.toString(g.getDeaths()));
		msg = msg.replace("{LIVES}", Integer.toString(g.getLives()));
		msg = msg.replace("{BANTIME}", Util.getDate(g.getBanTime()));
		msg = msg.replace("{BANADMIN}", g.getBanAdmin());
		msg = msg.replace("{BANREASON}", g.getBanReason());

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
		msg = msg.replace("{SIZE2}", Integer.toString(g1.getCuboid().getSize() * 2 + 1));
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
