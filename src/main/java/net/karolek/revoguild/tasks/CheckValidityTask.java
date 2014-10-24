package net.karolek.revoguild.tasks;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.scheduler.BukkitRunnable;

public class CheckValidityTask extends BukkitRunnable {

	@Override
	public void run() {
		for (Guild g : Manager.GUILD.getGuilds()) {
			if (g.getExpireTime() < System.currentTimeMillis()) {
				Manager.GUILD.removeGuild(g);
				Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_EXPIRED, g).replace("{X}", Integer.toString(g.getCuboid().getCenterX())).replace("{Z}", Integer.toString(g.getCuboid().getCenterZ())));
			}
		}
	}

}
