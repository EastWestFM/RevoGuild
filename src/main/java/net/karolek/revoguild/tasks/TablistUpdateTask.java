package net.karolek.revoguild.tasks;

import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.tablist.Tab;

import org.bukkit.scheduler.BukkitRunnable;

public class TablistUpdateTask extends BukkitRunnable {

	@Override
	public void run() {
		for (Tab t : Manager.TAB.getTabLists().values()) {
			Manager.TAB.updateTablist(t);
		}
	}

}
