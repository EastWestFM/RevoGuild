package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.AllianceManager;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class AsyncTagListener implements Listener {

    @EventHandler
    public void onAsyncTag(AsyncPlayerReceiveNameTagEvent e) {

        Player p = e.getPlayer();
        Player n = e.getNamedPlayer();

        Guild g = GuildManager.getGuild(p);
        Guild o = GuildManager.getGuild(n);

        Scoreboard sb = p.getScoreboard();

        String tag = Config.TAG_COLOR_NOGUILD;

        if (o == null) {
            tag = Config.TAG_COLOR_NOGUILD;
        } else if (o.equals(g)) {

            if (o.isPvp()) {
                tag = Config.TAG_COLOR_FRIENDPVP;
            } else {
                tag = Config.TAG_COLOR_FRIEND;
            }

        } else if ((g != null) && (AllianceManager.hasAlliance(g, o))) {
            tag = Config.TAG_COLOR_ALLIANCE;
        } else {
            tag = Config.TAG_COLOR_ENEMY;
        }

        Team t = sb.getTeam(n.getName());

        if (t == null) {
            t = sb.registerNewTeam(n.getName());
            t.addPlayer(n);
        }

        String format = Config.TAG_FORMAT;
        if (o == null)
            format = "{COLOR}";

        format = format.replace("{COLOR}", tag);

        format = o == null ? Util.fixColor(format) : Lang.parse(format, o);

        format = format.substring(0, Math.min(format.length(), 16));

        if (!t.getPrefix().equals(format))
            t.setPrefix(format);

    }
}
