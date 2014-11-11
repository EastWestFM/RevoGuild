package net.karolek.revoguild.commands.guild.admin;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class DeleteCommand extends SubCommand {

    public DeleteCommand() {
        super("usun", "usuwanie wybranej gildii", "/ga usun <tag/nazwa>", "revoguild.admin.delete", "delete");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 1)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(args[0]);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

        GuildManager.removeGuild(g);
        return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.ADMIN_BC_GUILD_DELETED, g, p));
    }
}
