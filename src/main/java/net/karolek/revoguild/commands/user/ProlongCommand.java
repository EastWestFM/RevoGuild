package net.karolek.revoguild.commands.user;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.TimeUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class ProlongCommand extends SubCommand {

	public ProlongCommand() {
		super("przedluz", "przedluzanie waznosci gildii", "", "rg.cmd.user.prolong", "prolong", "addtime");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);
		
		long t = g.getExpireTime();
		
		if((t - System.currentTimeMillis()) >= TimeUtil.DAY.getTime(Config.TIME_MAX)) 
			return Util.sendMsg(p, Lang.ERROR_CANT_ADD_TIME);
		
		if(!ItemUtil.checkAndRemove(ItemUtil.getItems(Config.COST_PROLONG, 1), p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);
		
		g.setExpireTime(t + TimeUtil.DAY.getTick(Config.TIME_ADD));
		g.update(false);
		
		return Util.sendMsg(p, Lang.INFO_PROLONGED_VALIDITY);
	}
}
