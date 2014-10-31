package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

	@EventHandler
	public void onInventoryClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		
		Guild g = GuildManager.getGuild(p);
		
		if(g == null) 
			return;
		
		
		Inventory inv = e.getInventory();
		if (!Util.fixColor(Config.TREASURE_TITLE).equalsIgnoreCase(inv.getName()))
			return;
		g.closeTreasure(p, inv);
	}

}
