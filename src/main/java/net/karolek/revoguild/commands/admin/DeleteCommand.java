package net.karolek.revoguild.commands.admin;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class DeleteCommand extends SubCommand {

	public DeleteCommand() {
		super("usun", "usuwanie wybranej gildii", "<tag/nazwa>", "rg.cmd.admin.delete", "delete");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = Manager.GUILD.getGuild(args[0]);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

		Manager.GUILD.removeGuild(g);
		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.ADMIN_BC_GUILD_DELETED, g, p));
	}
}
