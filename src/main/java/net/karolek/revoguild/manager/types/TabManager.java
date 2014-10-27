package net.karolek.revoguild.manager.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.WeakHashMap;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

import lombok.Getter;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.data.Tablist;
import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.tablist.Tab;
import net.karolek.revoguild.tablist.TabCell;
import net.karolek.revoguild.utils.PacketUtil;
import net.karolek.revoguild.utils.Reflection;
import net.karolek.revoguild.utils.Util;
import net.karolek.revoguild.utils.Reflection.ConstructorInvoker;
import net.karolek.revoguild.utils.Reflection.FieldAccessor;

public class TabManager implements IManager {

	private Class<?>							packetClass			= null;
	private Class<?>							packetClassPlayer	= null;
	private ConstructorInvoker				con					= null;
	private ConstructorInvoker				conPlayer			= null;
	private FieldAccessor<String>			a						= null;
	private FieldAccessor<String>			b						= null;
	private FieldAccessor<String>			c						= null;
	private FieldAccessor<String>			d						= null;
	@SuppressWarnings("rawtypes")
	private FieldAccessor<Collection>	e						= null;
	private FieldAccessor<Integer>		f						= null;
	private FieldAccessor<Integer>		g						= null;

	@Getter
	private WeakHashMap<Player, Tab>		tabLists				= null;

	public Tab createTab(Player player) {
		Tab tab = new Tab(player);
		tabLists.put(player, tab);
		return tab;
	}

	public Tab getTab(Player player) {
		return tabLists.get(player);
	}

	public void initTablist(Tab t) {
		for (Entry<Integer, String> e : Tablist.slots.entrySet()) {
			String[] pns = e.getValue().split("\\|");
			String prefix = pns.length > 0 ? pns[0] : "";
			String name = pns.length > 1 ? pns[1] : "";
			String suffix = pns.length > 2 ? pns[2] : "";
			t.setSlot(e.getKey(), parse(prefix, t.getPlayer()), name, parse(suffix, t.getPlayer()));
		}
	}
	
	public void updateTablist(Tab t) {
		for (Entry<Integer, String> e : Tablist.slots.entrySet()) {
			String[] pns = e.getValue().split("\\|");
			String prefix = pns.length > 0 ? pns[0] : "";
			String suffix = pns.length > 2 ? pns[2] : "";
			TabCell tc = t.getSlot(e.getKey());
			if(tc == null) continue;
			tc.updatePrefixAndSuffix(parse(prefix, t.getPlayer()), parse(suffix, t.getPlayer()));
		}
	}

	private String parse(String s, Player p) {
		//TODO do zmiany w przyszłości bo mega lagi :D
		Map<String, User> unsortedUsersMap = Manager.USER.getUsers();
		SortedMap<String, User> sortedUsersMap = ImmutableSortedMap.copyOf(unsortedUsersMap, Ordering.natural().reverse().onResultOf(Functions.forMap(unsortedUsersMap)).compound(Ordering.natural().reverse()));
		List<User> users = new ArrayList<>(sortedUsersMap.values());

		Map<String, Guild> unsortedGuildsMap = Manager.GUILD.getGuilds();
		SortedMap<String, Guild> sortedGuildsMap = ImmutableSortedMap.copyOf(unsortedGuildsMap, Ordering.natural().reverse().onResultOf(Functions.forMap(unsortedGuildsMap)).compound(Ordering.natural().reverse()));
		List<Guild> guilds = new ArrayList<>(sortedGuildsMap.values());

		User pU = Manager.USER.getUser(p);

		for (int i = 0; i < 10; i++) {
			if (i >= users.size()) {
				s = StringUtils.replace(s, "{PTOP-" + (i + 1) + "}", "brak");
			} else {
				User u = users.get(i);
				s = StringUtils.replace(s, "{PTOP-" + (i + 1) + "}", u == null ? "brak" : u.getName());
			}
		}

		for (int i = 0; i < 10; i++) {
			if (i >= guilds.size()) {
				s = StringUtils.replace(s, "{GTOP-" + (i + 1) + "}", "brak");
			} else {
				Guild g = guilds.get(i);
				s = StringUtils.replace(s, "{GTOP-" + (i + 1) + "}", g == null ? "brak" : g.getTag() + " [" + g.getPoints() + "]");
			}
		}

		s = Lang.parse(s, pU);

		s = StringUtils.replace(s, "{ONLINE}", Integer.toString(Util.getOnlinePlayers().size()));
		s = StringUtils.replace(s, "{TIME}", Util.getTime(System.currentTimeMillis()));
		
		Guild g = Manager.GUILD.getGuild(p);
		s = StringUtils.replace(s, "{TAG}", g == null ? "brak" : g.getTag());
		s = StringUtils.replace(s, "{PING}", Integer.toString(PacketUtil.getPing(p)));

		return Util.fixColor(s);
	}

	public Object createTeamPacket(String name, String display, String prefix, String suffix, int flag, String... members) {
		Object packet = this.con.invoke();

		a.set(packet, name.length() > 16 ? name.substring(0, 16) : name);
		f.set(packet, flag);
		if (flag == 0 || flag == 2) {

			if (display == null) {
				b.set(packet, "");
			} else if (display.length() > 16) {
				b.set(packet, display.substring(0, 16));
			} else {
				b.set(packet, display);
			}

			if (prefix == null) {
				c.set(packet, "");
			} else if (prefix.length() > 16) {
				c.set(packet, prefix.substring(0, 16));
			} else {
				c.set(packet, prefix);
			}

			if (suffix == null) {
				d.set(packet, "");
			} else if (suffix.length() > 16) {
				d.set(packet, suffix.substring(0, 16));
			} else {
				d.set(packet, suffix);
			}

			g.set(packet, 0);
			if (flag == 0) {
				e.set(packet, Arrays.asList(members));
			}
		}
		return packet;
	}

	public Object createPlayerPacket(String name, boolean visible, int ping) {
		return conPlayer.invoke(name, visible, ping);
	}

	@Override
	public void enable() throws Exception {
		this.tabLists = new WeakHashMap<>();
		this.packetClass = Reflection.getMinecraftClass("PacketPlayOutScoreboardTeam");
		this.packetClassPlayer = Reflection.getMinecraftClass("PacketPlayOutPlayerInfo");
		this.conPlayer = Reflection.getConstructor(packetClassPlayer, String.class, boolean.class, int.class);
		this.con = Reflection.getConstructor(packetClass);
		this.a = Reflection.getField(packetClass, "a", String.class);
		this.b = Reflection.getField(packetClass, "b", String.class);
		this.c = Reflection.getField(packetClass, "c", String.class);
		this.d = Reflection.getField(packetClass, "d", String.class);
		this.e = Reflection.getField(packetClass, "e", Collection.class);
		this.f = Reflection.getField(packetClass, "f", int.class);
		this.g = Reflection.getField(packetClass, "g", int.class);

	}

	@Override
	public void disable() {
		this.tabLists.clear();
		this.tabLists = null;
		this.packetClass = null;
		this.packetClassPlayer = null;
		this.conPlayer = null;
		this.con = null;
		this.a = null;
		this.b = null;
		this.c = null;
		this.d = null;
		this.e = null;
		this.f = null;
		this.g = null;
	}

}
