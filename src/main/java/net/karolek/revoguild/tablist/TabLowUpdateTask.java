package net.karolek.revoguild.tablist;

import java.util.Map.Entry;

import net.karolek.revoguild.data.Tablist;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class TabLowUpdateTask extends BukkitRunnable {

	@Override
	public void run() {
		for (Entry<Player, Tab> e : TabManager.getTablists().entrySet()) {
			Tab t = e.getValue();
			for(int i : Tablist.updateSlots) 
				TabManager.updateSlot(t, i);
		}
	}
	
}
