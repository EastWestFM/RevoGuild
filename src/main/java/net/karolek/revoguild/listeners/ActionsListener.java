package net.karolek.revoguild.listeners;


import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class ActionsListener implements Listener {
	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockBreak(BlockBreakEvent e) {
		if(!Config.ACTIONS_BLOCK_BREAK)
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlock().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBlockPlace(BlockPlaceEvent e) {
		if(!Config.ACTIONS_BLOCK_PLACE)
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlock().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBucketEmpty(PlayerBucketEmptyEvent e) {
		if(!Config.ACTIONS_BUCKET_EMPTY)
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlockClicked().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onBucketFill(PlayerBucketFillEvent e) {
		if(!Config.ACTIONS_BUCKET_FILL)
		e.setCancelled(cancelAction(e.getPlayer(), e.getBlockClicked().getLocation(), Lang.ERROR_NOT_YOUR_GUILD));
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.LOWEST)
	public void onInteract(PlayerInteractEvent e) {
		if(Config.ACTIONS_PROTECTEDID.size() == 0) return;
		
		Player p = e.getPlayer();
		Block b = e.getClickedBlock();
		
		if(b == null) return;
		
		if(!Config.ACTIONS_PROTECTEDID.contains(b.getTypeId())) return;
		
		Guild g = GuildManager.getGuild(b.getLocation());
		
		if(g == null) return;
		
		if(g.isMember(UserManager.getUser(p))) return;
		
		e.setCancelled(true);
		Util.sendMsg(p, Lang.ERROR_CANT_USE);
	}

	private boolean cancelAction(Player p, Location l, String message) {
		if (p.isOp())
			return false;

		Guild g = GuildManager.getGuild(l);
		if (g == null)
			return false;

		if (g.isMember(UserManager.getUser(p)))
			return false;

		Util.sendMsg(p, message);
		return true;
	}
}
