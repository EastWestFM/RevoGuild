package net.karolek.revoguild.listeners;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

public class BuildListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlock().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlock().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBucketEmpty(PlayerBucketEmptyEvent e) {
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlockClicked().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBucketFill(PlayerBucketFillEvent e) {
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlockClicked().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	private boolean cancelAction(Player p, Location l, String message) {
		if (p.isOp())
			return false;

		Guild g = Manager.GUILD.getGuild(l);
		if (g == null)
			return false;

		if (g.isMember(p.getUniqueId()))
			return false;

		Util.sendMsg(p, message);
		return true;
	}
}
