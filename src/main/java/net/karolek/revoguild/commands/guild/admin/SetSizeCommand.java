package net.karolek.revoguild.commands.guild.admin;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class SetSizeCommand extends SubCommand {

    public SetSizeCommand() {
        super("setsize", "zmiana rozmiaru cuboida wybranej gildii", "<tag/nazwa> <rozmiar>", "revoguild.admin.setsize", "ustawrozmiar");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 2)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(args[0]);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

        if (!Util.isInteger(args[1]))
            return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);

        int value = Integer.parseInt(args[1]);

        if (value < Config.CUBOID_SIZE_START || value > Config.CUBOID_SIZE_MAX)
            return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);

        g.getCuboid().setSize(value);
        g.update(false);

        return Util.sendMsg(p, Lang.INFO_SIZE_CHANGED);
    }
}
