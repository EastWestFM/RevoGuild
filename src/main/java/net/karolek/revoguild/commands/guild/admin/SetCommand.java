package net.karolek.revoguild.commands.guild.admin;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class SetCommand extends SubCommand {

    public SetCommand() {
        super("set", "zmiana wartosci wybranej gildii", "/ga set <tag/nazwa> <leader|owner|lives|pvp|size> <wartosc>", "revoguild.admin.set", "ustawrozmiar");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 3)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(args[0]);

        String value = args[2];
        User user = null;

        switch(args[1]) {
            case "leader":
                user = UserManager.getUser(value);
                if(user == null)
                    return Util.sendMsg(p, Lang.ERROR_CANT_FIND_USER);
                if(!g.isMember(user))
                    return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);
                g.getLeader().set(user);
                break;
            case "owner":
                user = UserManager.getUser(value);
                if(user == null)
                    return Util.sendMsg(p, Lang.ERROR_CANT_FIND_USER);
                if(!g.isMember(user))
                    return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);
                g.getOwner().set(user);
                break;
            case "lives":
                if(!Util.isInteger(value))
                    return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);
                int lives = Integer.parseInt(value);
                if(lives < 1 || lives > Config.UPTAKE_LIVES_MAX)
                    return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);
                g.getLives().set(lives);
                break;
            case "size":
                if (!Util.isInteger(args[1]))
                    return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);

                int v = Integer.parseInt(args[1]);

                if (v < Config.CUBOID_SIZE_START || v > Config.CUBOID_SIZE_MAX)
                    return Util.sendMsg(p, Lang.ERROR_BAD_INTEGER);

                g.getCuboid().setSize(v);
                g.update(false);
                break;
            case "pvp":
                boolean pvp = Util.getBoolean(value);
                g.setPvp(pvp);
                g.update(false);
                break;
            default:
                return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));
        }

        return Util.sendMsg(p, Lang.parse(Lang.INFO_CHANGED_GUILD, g).replace("{FIELD}", args[1].toLowerCase()));
    }
}
