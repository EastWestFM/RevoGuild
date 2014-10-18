package net.karolek.revoguild.commands.user;

import java.util.List;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OwnerCommand extends SubCommand {

	public OwnerCommand() {
		super("zalozyciel", "zmienianie zalozyciela gildii", "", "rg.cmd.user.owner", "owner");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(p.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

		@SuppressWarnings("deprecation")
		Player o = Bukkit.getPlayer(args[0]);

		if (o == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_PLAYER);

		if (!g.isMember(o.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);

		List<ItemStack> items = ItemUtil.getItems(Config.COST_OWNER, 1);

		if (!ItemUtil.checkAndRemove(items, p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		g.setOwner(o.getUniqueId());
		g.update(true);

		Util.sendMsg(p, Lang.INFO_OWNER_CHANGED);
		return Util.sendMsg(o, Lang.INFO_NOW_OWNER);
	}
}
