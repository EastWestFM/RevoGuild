package net.karolek.revoguild.tablist.update;

import java.util.Map.Entry;

import net.karolek.revoguild.data.TabScheme;
import net.karolek.revoguild.managers.TabManager;
import net.karolek.revoguild.tablist.Tab;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabLowUpdateTask extends BukkitRunnable {

	@Override
	public void run() {
		for (Entry<Player, Tab> e : TabManager.getTablists().entrySet()) {
			Tab t = e.getValue();
			for(int i : TabScheme.updateSlots) 
				TabManager.updateSlot(t, i);
		}
	}
	
}
