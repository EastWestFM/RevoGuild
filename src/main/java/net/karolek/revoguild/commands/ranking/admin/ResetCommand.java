package net.karolek.revoguild.commands.ranking.admin;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class ResetCommand extends SubCommand {

    public ResetCommand() {
        super("reset", "reset rankingu wybranego gracza", "/ra reset <gracz>", "revoguild.admin.reset");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 1)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        User u = UserManager.getUserByName(args[0]);

        if (u == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_USER);

        u.getPoints().set(Config.RANKING_STARTPOINTS);
        u.getKills().set(0);
        u.getDeaths().set(0);
        TabThread.restart();
        return Util.sendMsg(p, Lang.INFO_RESETED);
    }
}
