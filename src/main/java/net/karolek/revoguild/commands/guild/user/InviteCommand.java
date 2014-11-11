package net.karolek.revoguild.commands.guild.user;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand extends SubCommand {

    public InviteCommand() {
        super("zapros", "zapraszanie graczy do gildii", "/g zapros <gracz>", "sguilds.cmd.user.invite", "dodaj", "invite");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {

        if (args.length != 1)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(p);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

        if (!g.isLeader(UserManager.getUser(p)))
            return Util.sendMsg(p, Lang.ERROR_NOT_LEADER);

        @SuppressWarnings("deprecation")
        Player o = Bukkit.getPlayer(args[0]);
        User oU = UserManager.getUser(o);

        if (o == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_PLAYER);

        if (g.isMember(oU))
            return Util.sendMsg(p, Lang.ERROR_PLAYER_IS_MEMBER);

        if (!g.addInvite(oU)) {
            g.removeInvite(oU);
            Util.sendMsg(p, Lang.parse(Lang.INFO_INVITE_BACK, o));
            return Util.sendMsg(o, Lang.parse(Lang.INFO_INVITE_CANCEL, g));
        }

        Util.sendMsg(p, Lang.parse(Lang.INFO_INVITE_SEND, o));
        return Util.sendMsg(o, Lang.parse(Lang.INFO_INVITE_NEW, g));


    }

}
