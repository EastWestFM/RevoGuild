package net.karolek.revoguild.commands.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class ListCommand extends SubCommand {

	public ListCommand() {
		super("lista", "lista wszystkich gildii", "", "rg.cmd.user.list", "list");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Util.sendMsg(p, Lang.LIST_HEADER);
		for (Guild g : Manager.GUILD.getGuilds())
			Util.sendMsg(p, Lang.parse(Lang.LIST_ELEMENT, g));
		Util.sendMsg(p, Lang.LIST_FOOTER);
		return true;
	}
}
