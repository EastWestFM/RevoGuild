package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.manager.Manager;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class CrystalListener implements Listener {

	@EventHandler
	public void onPistonEvent(BlockPistonExtendEvent e) {
		Guild g = Manager.GUILD.getGuild(e.getBlock().getLocation());

		if (g == null)
			return;

		if (g.getCrystal(e.getBlock().getLocation()) != null) {
			e.setCancelled(true);
			return;
		}

		for (Block b : e.getBlocks()) {
			BlockFace face = e.getDirection();
			if (g.getCrystal(b.getLocation().add(face.getModX(), face.getModY(), face.getModZ())) != null) {
				e.setCancelled(true);
				return;
			}
		}

	}

}
