package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class ListCommand extends SubCommand {

	public ListCommand() {
		super("lista", "lista wszystkich gildii", "", "revoguild.list", "list");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Util.sendMsg(p, Lang.LIST_GUILD_HEADER);
		for (Guild g : GuildManager.getGuilds().values())
			Util.sendMsg(p, Lang.parse(Lang.LIST_GUILD_ELEMENT, g));
		Util.sendMsg(p, Lang.LIST_GUILD_FOOTER);
		return true;
	}
}
