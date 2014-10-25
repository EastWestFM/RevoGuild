package net.karolek.revoguild.commands.user;

import java.util.List;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LeaderCommand extends SubCommand {

	public LeaderCommand() {
		super("lider", "zmienianie lidera gildii", "", "rg.cmd.user.leader", "leader");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(Manager.USER.getUser(p)))
			return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

		@SuppressWarnings("deprecation")
		Player o = Bukkit.getPlayer(args[0]);
		User u = Manager.USER.getUser(p);
		if (o == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_PLAYER);

		if (!g.isMember(u))
			return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);

		List<ItemStack> items = ItemUtil.getItems(Config.COST_LEADER, 1);

		if (!ItemUtil.checkAndRemove(items, p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		g.setLeader(u);

		Util.sendMsg(p, Lang.INFO_LEADER_CHANGED);
		return Util.sendMsg(o, Lang.INFO_NOW_LEADER);
	}
}
