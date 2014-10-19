package net.karolek.revoguild.listeners;

import net.karolek.revoguild.manager.Manager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinQuitListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Manager.TAG.getNameTag().initPlayer(e.getPlayer());
	}

}
