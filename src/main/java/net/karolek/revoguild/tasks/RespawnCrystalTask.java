package net.karolek.revoguild.tasks;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.UptakeUtil;

import org.bukkit.scheduler.BukkitRunnable;

public class RespawnCrystalTask extends BukkitRunnable {

	@Override
	public void run() {
		for(Guild g : GuildManager.getGuilds().values())
			UptakeUtil.respawnGuild(g);
	}

}
