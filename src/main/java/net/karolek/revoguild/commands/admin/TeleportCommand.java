package net.karolek.revoguild.commands.admin;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class TeleportCommand extends SubCommand {

	public TeleportCommand() {
		super("tp", "teleport do wybranej gildii", "<tag/nazwa>", "rg.cmd.admin.tp", "teleport");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = Manager.GUILD.getGuild(args[0]);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

		p.teleport(g.getCuboid().getCenter());
		return Util.sendMsg(p, Lang.TELEPORT_END);
	}
}
