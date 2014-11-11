package net.karolek.revoguild.commands.guild.admin;

import net.karolek.revoguild.base.Cuboid;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetCuboidCommand extends SubCommand {

    public SetCuboidCommand() {
        super("setcuboid", "zmiana cuboida wybranej gildii", "/ga setcub <tag/nazwa>", "revoguild.admin.setcuboid", "setcub", "ustawteren");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 1)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(args[0]);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

        Location l = p.getLocation();

        if (!GuildManager.canCreateGuild(l))
            return Util.sendMsg(p, Lang.ERROR_CANT_SET_CUBOID);

        Cuboid o = g.getCuboid();
        GuildManager.removeGuildRoom(g);

        Cuboid n = new Cuboid(l.getWorld().getName(), l.getBlockX(), l.getBlockZ(), o.getSize());
        g.setCuboid(n);
        g.setHome(l);
        g.update(false);
        GuildManager.setGuildRoom(g);
        p.teleport(g.getCuboid().getCenter());
        return Util.sendMsg(p, Lang.INFO_CUBOID_SET);
    }
}
