package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.NameTagManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class PvpCommand extends SubCommand {

    public PvpCommand() {
        super("pvp", "zmiana statusu pvp w gildii", "/g pvp", "revoguild.pvp", "ff", "friendlyfire");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        Guild g = GuildManager.getGuild(p);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

        if (!g.isOwner(UserManager.getUser(p)))
            return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

        g.setPvp(!g.isPvp());
        g.update(false);

        for (Player o : g.getOnlineMembers())
            Util.sendMsg(o, g.isPvp() ? Lang.INFO_PVP_ON : Lang.INFO_PVP_OFF);

        NameTagManager.setPvp(g);

        return true;
    }
}
