package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class GuildCommandsListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();

        Guild g = GuildManager.getGuild(p.getLocation());


        if (!Config.CUBOID_DISABLEDCMD_ENABLED) return;
        if (g == null) return;
        if (g.isMember(UserManager.getUser(p))) return;
        for (String s : Config.CUBOID_DISABLEDCMD_COMMANDS) {
            if (!msg.contains("/" + s))
                continue;
            e.setCancelled(true);
            if (Config.CUBOID_DISABLEDCMD_NOTIFY_ENABLED)
                Util.sendMsg(p, Util.fixColor(Config.CUBOID_DISABLEDCMD_NOTIFY_MESSAGE));
        }

    }

}
