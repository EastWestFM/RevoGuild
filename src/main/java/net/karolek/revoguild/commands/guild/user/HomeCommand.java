package net.karolek.revoguild.commands.guild.user;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.TeleportManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class HomeCommand extends SubCommand {

	public HomeCommand() {
		super("dom", "teleport do domu gildii", "", "revoguild.home", "baza", "home");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		TeleportManager.teleport(p, g.getHome(), Config.TIME_TELEPORT);
		return true;
	}
}
