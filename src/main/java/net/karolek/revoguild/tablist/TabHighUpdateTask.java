package net.karolek.revoguild.tablist;

import java.util.Map;

import net.karolek.revoguild.data.Tablist;

import org.bukkit.scheduler.BukkitRunnable;

public class TabHighUpdateTask extends BukkitRunnable {

	@Override
	public void run() {
		Map<Integer, String> slots = Tablist.slots;
		for (Tab t : TabManager.getTablists().values()) {
			for (int slot : slots.keySet()) {
				System.out.println(slot);
				TabManager.updateSlot(t, slot);
			}
		}
	}

}
