package net.karolek.revoguild.commands.ranking.admin;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {

    public SetCommand() {
        super("set", "ustawianie wartosci wybranego gracza", "<gracz> <kills|deaths|points> <wartosc>", "revoguild.admin.set", "ustaw");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 3)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        User u = UserManager.getUserByName(args[0]);

        if (u == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_USER);

        if (!Util.isInteger(args[2]))
            return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);

        int value = Integer.parseInt(args[2]);

        if (value < 0)
            return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);

        switch (args[1]) {
            case "kills":
                u.getKills().set(value);
                break;
            case "deaths":
                u.getDeaths().set(value);
                break;
            case "points":
                u.getPoints().set(value);
                break;
            default:
                return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));
        }

        TabThread.restart();
        return Util.sendMsg(p, Lang.INFO_SETTED);
    }
}
