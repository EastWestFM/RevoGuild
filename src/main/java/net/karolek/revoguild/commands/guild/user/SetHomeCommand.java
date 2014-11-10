package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class SetHomeCommand extends SubCommand {

    public SetHomeCommand() {
        super("ustawdom", "ustawianie domu gildii", "", "revoguild.sethome", "ustawbaze", "sethome");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        Guild g = GuildManager.getGuild(p);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

        if (!g.isOwner(UserManager.getUser(p)))
            return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

        Guild o = GuildManager.getGuild(p.getLocation());

        if (!g.equals(o))
            return Util.sendMsg(p, Lang.ERROR_CANT_SET_HOME_OUTSIDE_CUBOID);

        g.setHome(p.getLocation());
        g.update(true);

        return Util.sendMsg(p, Lang.INFO_HOME_SET);
    }
}
