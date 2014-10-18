package net.karolek.revoguild.commands.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class SetHomeCommand extends SubCommand {

	public SetHomeCommand() {
		super("ustawdom", "ustawianie domu gildii", "", "rg.cmd.user.sethome", "ustawbaze", "sethome");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(p.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

		if (Manager.GUILD.getGuild(p.getLocation()) != null)
			return Util.sendMsg(p, Lang.ERROR_CANT_SET_HOME_OUTSIDE_CUBOID);

		g.setHome(p.getLocation());
		g.update(true);

		return Util.sendMsg(p, Lang.INFO_HOME_SET);
	}
}
