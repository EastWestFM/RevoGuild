package net.karolek.revoguild.nametags.modes;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.nametags.NameTag;
import net.karolek.revoguild.nametags.NameTagMode;
import net.karolek.revoguild.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.kitteh.tag.TagAPI;

public class TagApiNameTag implements NameTag {

    @Override
    public void initPlayer(Player p) {
        p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    @Override
    public void createGuild(Guild g, Player p) {
        TagAPI.refreshPlayer(p);
    }

    @Override
    public void removeGuild(Guild g) {
        for (Player p : Util.getOnlinePlayers())
            TagAPI.refreshPlayer(p);
    }

    @Override
    public void setPvp(Guild g) {
        for (Player p : g.getOnlineMembers())
            TagAPI.refreshPlayer(p);
    }

    @Override
    public void joinToGuild(Guild g, Player p) {
        for (Player pl : g.getOnlineMembers())
            TagAPI.refreshPlayer(pl);
    }

    @Override
    public void leaveFromGuild(Guild g, OfflinePlayer p) {
        for (Player pl : g.getOnlineMembers())
            TagAPI.refreshPlayer(pl);
        if (p.isOnline())
            TagAPI.refreshPlayer(p.getPlayer());
    }

    @Override
    public void createAlliance(Guild g, Guild o) {
        for (Player p : g.getOnlineMembers())
            TagAPI.refreshPlayer(p);
        for (Player p : o.getOnlineMembers())
            TagAPI.refreshPlayer(p);
    }

    @Override
    public void removeAlliance(Guild g, Guild o) {
        for (Player p : g.getOnlineMembers())
            TagAPI.refreshPlayer(p);
        for (Player p : o.getOnlineMembers())
            TagAPI.refreshPlayer(p);
    }

    @Override
    public NameTagMode getNameTagMode() {
        return NameTagMode.TAG_API;
    }

}
