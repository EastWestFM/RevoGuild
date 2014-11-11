package net.karolek.revoguild.commands.ranking;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class RankingCommand extends SubCommand {

    public RankingCommand() {
        super("ranking", "sprawdzanie rankingu gracza", "/ranking [gracz]", "revoguild.ranking", "elo", "gracz", "points");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        User u = UserManager.getUser(p);
        if (args.length == 0)
            return Util.sendMsg(p, Lang.parse(Lang.INFO_PLAYER, u));

        if (args.length == 1) {
            User o = UserManager.getUserByName(args[0]);
            if (o == null)
                return Util.sendMsg(p, Lang.ERROR_CANT_FIND_PLAYER);
            return Util.sendMsg(p, Lang.parse(Lang.INFO_PLAYER, o));

        }
        return false;
    }
}
