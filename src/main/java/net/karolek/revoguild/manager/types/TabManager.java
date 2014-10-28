package net.karolek.revoguild.manager.types;

import java.util.*;
import java.util.Map.Entry;

import lombok.AllArgsConstructor;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.tasks.TablistUpdateTask;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
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
import org.bukkit.scheduler.BukkitRunnable;

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

    private RankList rankList;
    private int taskId = -1;

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

		User pU = Manager.USER.getUser(p);

        List<RankList.Data> playerList = rankList.getTopPlayers();
        for (int i = 0; i < Math.min(10, playerList.size()); i++) {
            if (i >= playerList.size()) {
                s = StringUtils.replace(s, "{PTOP-" + (i + 1) + "}", "brak");
            } else {
                RankList.Data u = playerList.get(i);
                s = StringUtils.replace(s, "{PTOP-" + (i + 1) + "}", u == null ? "brak" : u.getName());
            }
        }

        List<RankList.Data> guildList = rankList.getTopGuilds();
        for (int i = 0; i < 10; i++) {
			if (i >= guildList.size()) {
				s = StringUtils.replace(s, "{GTOP-" + (i + 1) + "}", "brak");
			} else {
                RankList.Data g = guildList.get(i);
				s = StringUtils.replace(s, "{GTOP-" + (i + 1) + "}", g == null ? "brak" : g.getName() + " [" + g.getPoints() + "]");
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
        int sec = 60*2;
        // TODO aktualizacja czasu i innych pierdol z innym odswiezeniem
        taskId = new RankTask().runTaskTimerAsynchronously(GuildPlugin.getPlugin(), 0L, sec*20L).getTaskId();

		this.tabLists = new WeakHashMap<>();
        this.rankList = new RankList();
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
        if(taskId != -1){
            Bukkit.getScheduler().cancelTask(taskId);
        }
        this.rankList = null;
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

    private Comparator<User> statsComparator = new Comparator<User>() {
        @Override
        public int compare(User o1, User o2) {
            return o2.getPoints() - o1.getPoints();
        }
    };

    private Comparator<Guild> GUILD_COMPARATOR = new Comparator<Guild>() {
        @Override
        public int compare(Guild o1, Guild o2) {
            return o2.getPoints() - o1.getPoints();
        }
    };


    private class RankTask extends BukkitRunnable {

        @Override
        public void run() {

            // gracze
            List<User> stats = new ArrayList<>();
            stats.addAll(Manager.USER.getUsers().values());
            Collections.sort(stats, statsComparator);
            List<RankList.Data> toAddPlayers = new LinkedList<>();
            for(int i=0; i < Math.min(10, stats.size()); i++){
                User u = stats.get(i);
                toAddPlayers.add(new RankList.Data(u.getName(), u.getPoints()));
            }

            rankList.setTopPlayers(toAddPlayers);

            // gildie
            List<Guild> guilds = new ArrayList<>();
            guilds.addAll(Manager.GUILD.getGuilds().values());
            Collections.sort(guilds, GUILD_COMPARATOR);
            List<RankList.Data> toAddGuilds = new LinkedList<>();
            for(int i=0; i < Math.min(10, guilds.size()); i++){
                Guild g = guilds.get(i);
                toAddGuilds.add(new RankList.Data(g.getTag(), g.getPoints()));
            }
            rankList.setTopGuilds(toAddGuilds);
            new TablistUpdateTask().runTaskLater(GuildPlugin.getPlugin(), 1L);
        }
    }
}
