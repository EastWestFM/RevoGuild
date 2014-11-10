package net.karolek.revoguild.tablist.update;

import net.karolek.revoguild.data.TabScheme;
import net.karolek.revoguild.managers.TabManager;
import net.karolek.revoguild.tablist.Tab;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Map;

public class TabHighUpdateTask extends BukkitRunnable {

    @Override
    public void run() {
        Map<Integer, String> slots = TabScheme.slots;
        for (Tab t : TabManager.getTablists().values()) {
            for (int slot : slots.keySet()) {
                TabManager.updateSlot(t, slot);
            }
        }
    }

}
