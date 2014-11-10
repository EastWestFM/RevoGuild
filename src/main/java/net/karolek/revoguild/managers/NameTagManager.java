package net.karolek.revoguild.managers;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.nametags.NameTag;
import net.karolek.revoguild.nametags.NameTagMode;
import net.karolek.revoguild.nametags.modes.ScoreBoardNameTag;
import net.karolek.revoguild.nametags.modes.TagApiNameTag;
import net.karolek.revoguild.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class NameTagManager {

    @Getter
    private static NameTag nameTag;

    public static void enable() {
        new BukkitRunnable() {

            @Override
            public void run() {
                switch (NameTagMode.getByName(Config.TAG_MODE)) {
                    case TAG_API:
                        Plugin p = Bukkit.getPluginManager().getPlugin("TagAPI");
                        if (p == null || !p.isEnabled()) {
                            Logger.warning("Can not find valid TagAPI instance!", "Using ScoreBoard nametags!");
                            nameTag = new ScoreBoardNameTag();
                            break;
                        }
                        nameTag = new TagApiNameTag();
                        break;
                    case SCOREBOARD:
                    default:
                        nameTag = new ScoreBoardNameTag();
                        break;
                }
                Logger.info("Using '" + nameTag.getClass().getSimpleName().replace("NameTag", "") + "' to support nametags!");
            }

        }.runTask(GuildPlugin.getPlugin());

    }

    public static void initPlayer(Player p) {
        if (nameTag == null)
            return;
        nameTag.initPlayer(p);
    }

    public static void createGuild(Guild g, Player p) {
        if (nameTag == null)
            return;
        nameTag.createGuild(g, p);
    }

    public static void removeGuild(Guild g) {
        if (nameTag == null)
            return;
        nameTag.removeGuild(g);
    }

    public static void setPvp(Guild g) {
        if (nameTag == null)
            return;
        nameTag.setPvp(g);
    }

    public static void joinToGuild(Guild g, Player p) {
        if (nameTag == null)
            return;
        nameTag.joinToGuild(g, p);
    }

    public static void leaveFromGuild(Guild g, OfflinePlayer p) {
        if (nameTag == null)
            return;
        nameTag.leaveFromGuild(g, p);
    }

    public static void createAlliance(Guild g, Guild o) {
        if (nameTag == null)
            return;
        nameTag.createAlliance(g, o);
    }

    public static void removeAlliance(Guild g, Guild o) {
        if (nameTag == null)
            return;
        nameTag.removeAlliance(g, o);
    }

    public static NameTagMode getNameTagMode() {
        if (nameTag == null)
            return null;
        return nameTag.getNameTagMode();
    }

}
