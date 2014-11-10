package net.karolek.revoguild.managers;

import lombok.Getter;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.data.TabScheme;
import net.karolek.revoguild.tablist.Tab;
import net.karolek.revoguild.tablist.TabCell;
import net.karolek.revoguild.tablist.update.RankList;
import net.karolek.revoguild.tablist.update.RankList.Data;
import net.karolek.revoguild.utils.PacketUtil;
import net.karolek.revoguild.utils.Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public class TabManager {

    @Getter
    private static final WeakHashMap<Player, Tab> tablists = new WeakHashMap<Player, Tab>();

    public static Tab createTab(Player p) {
        if (hasTab(p))
            return getTab(p);
        Tab t = new Tab(p);
        sendTab(t);
        tablists.put(p, t);
        return t;
    }

    public static boolean removeTab(Player p) {
        if (!hasTab(p))
            return false;
        Tab t = getTab(p);
        t.clear();
        tablists.remove(p);
        return true;
    }

    public static void updateAll() {
        for (Tab t : tablists.values())
            t.update();
    }

    public static Tab getTab(Player p) {
        return tablists.get(p);
    }

    public static boolean hasTab(Player p) {
        return tablists.containsKey(p);
    }

    public static void sendTab(Tab t) {
        for (Entry<Integer, String> e : TabScheme.slots.entrySet()) {
            String[] pns = e.getValue().split("\\|");
            String prefix = pns.length > 0 ? pns[0] : "";
            String name = pns.length > 1 ? pns[1] : "";
            String suffix = pns.length > 2 ? pns[2] : "";
            t.setSlot(e.getKey(), parse(prefix, t.getPlayer()), name, parse(suffix, t.getPlayer()));
        }
        t.send();
    }

    public static void updateSlot(Tab t, int slot) {
        TabCell tc = t.getSlot(slot);
        if (tc == null)
            return;
        String[] pns = TabScheme.slots.get(slot).split("\\|");
        String prefix = pns.length > 0 ? pns[0] : "";
        String suffix = pns.length > 2 ? pns[2] : "";
        tc.updatePrefixAndSuffix(parse(prefix, t.getPlayer()), parse(suffix, t.getPlayer()));
    }

    public static String parse(String s, Player p) {
        User pU = UserManager.getUser(p);

        List<Data<User>> playerList = RankList.getTopPlayers();
        for (int i = 0; i < 20; i++) {
            if (i >= playerList.size()) {
                s = StringUtils.replace(s, "{PTOP-" + (i + 1) + "}", "brak");
            } else {
                Data<User> u = playerList.get(i);
                s = StringUtils.replace(s, "{PTOP-" + (i + 1) + "}", u == null ? "brak" : u.getKey().getName());
            }
        }

        List<Data<Guild>> guildList = RankList.getTopGuilds();
        for (int i = 0; i < 20; i++) {
            if (i >= guildList.size()) {
                s = StringUtils.replace(s, "{GTOP-" + (i + 1) + "}", "brak");
            } else {
                Data<Guild> g = guildList.get(i);
                s = StringUtils.replace(s, "{GTOP-" + (i + 1) + "}", g == null ? "brak" : g.getKey().getTag() + " &7[" + g.getPoints() + "]");
            }
        }

        s = Lang.parse(s, pU);
        s = StringUtils.replace(s, "{ONLINE}", Integer.toString(Util.getOnlinePlayers().size()));
        s = StringUtils.replace(s, "{TIME}", Util.getTime(System.currentTimeMillis()));
        Guild g = GuildManager.getGuild(p);
        s = StringUtils.replace(s, "{TAG}", g == null ? "brak" : g.getTag());
        s = StringUtils.replace(s, "{PING}", Integer.toString(PacketUtil.getPing(p)));
        return Util.fixColor(s);
    }
}
