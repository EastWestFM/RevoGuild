package net.karolek.revoguild.commands.user;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class HomeCommand extends SubCommand {

	public HomeCommand() {
		super("dom", "teleport do domu gildii", "", "rg.cmd.user.home", "baza", "home");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		Manager.TELEPORT.teleport(p, g.getHome(), Config.TIME_TELEPORT);
		return true;
	}
}
