package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.TimeUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class ProlongCommand extends SubCommand {

	public ProlongCommand() {
		super("przedluz", "przedluzanie waznosci gildii", "", "revoguild.prolong", "prolong", "addtime");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		long t = g.getExpireTime().get();

		if ((t - System.currentTimeMillis()) >= TimeUtil.DAY.getTime(Config.TIME_MAX))
			return Util.sendMsg(p, Lang.ERROR_CANT_ADD_TIME);

		if (!ItemUtil.checkAndRemove(ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_PROLONG_VIP : Config.COST_PROLONG_NORMAL, 1), p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		g.getExpireTime().add(TimeUtil.DAY.getTick(Config.TIME_ADD));

		return Util.sendMsg(p, Lang.INFO_PROLONGED_VALIDITY);
	}
}
