package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class InfoCommand extends SubCommand {

	public InfoCommand() {
		super("info", "informacje o gildii", "<tag>", "revoguild.info", "informacje");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));
		
		Guild g = GuildManager.getGuild(args[0]);
		
		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);
		
		return Util.sendMsg(p, Lang.parse(Lang.INFO_GUILD, g));
	}
}
