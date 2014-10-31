package net.karolek.revoguild.commands.guild.user;

import java.util.List;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class OwnerCommand extends SubCommand {

	public OwnerCommand() {
		super("zalozyciel", "zmienianie zalozyciela gildii", "", "revoguild.owner", "owner");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(UserManager.getUser(p)))
			return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

		@SuppressWarnings("deprecation")
		Player o = Bukkit.getPlayer(args[0]);
		User u = UserManager.getUser(o);
		if (o == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_PLAYER);

		if (!g.isMember(u))
			return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);

		List<ItemStack> items = ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_OWNER_VIP : Config.COST_OWNER_NORMAL, 1);

		if (!ItemUtil.checkAndRemove(items, p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		g.setOwner(u);

		Util.sendMsg(p, Lang.INFO_OWNER_CHANGED);
		return Util.sendMsg(o, Lang.INFO_NOW_OWNER);
	}
}
