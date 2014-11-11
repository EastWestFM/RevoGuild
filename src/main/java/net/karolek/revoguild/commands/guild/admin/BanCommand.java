package net.karolek.revoguild.commands.guild.admin;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

public class BanCommand extends SubCommand {

    public BanCommand() {
        super("ban", "banowanie wybranej gildii", "/ga ban <tag/nazwa> <czas> <powod>", "revoguild.admin.ban", "zbanuj");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length < 3)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(args[0]);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

        long time = Util.parseDateDiff(args[1], true);
        String reason = StringUtils.join(args, " ", 2, args.length);
        String admin = p.getName();

        if (!g.ban(admin, reason, time))
            return Util.sendMsg(p, Lang.ERROR_GUILD_HAVE_BAN);

        return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.ADMIN_BC_GUILD_BANNED, g, p));
    }
}
