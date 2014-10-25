package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.manager.Manager;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Manager.TAG.getNameTag().initPlayer(e.getPlayer());
		User u = Manager.USER.getUser(e.getPlayer());
		if(u == null) u = Manager.USER.createUser(e.getPlayer());
		
		u.setJoinTime(System.currentTimeMillis());
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		leave(e.getPlayer());
	}
	
	@EventHandler
	public void onKick(PlayerKickEvent e) {
		leave(e.getPlayer());
	}
	
	
	private void leave(Player p) {
		User u = Manager.USER.getUser(p);
		if(u == null) throw new RuntimeException("User not exists!");
		
		long time = System.currentTimeMillis() - u.getJoinTime();
		u.addTimePlay((int) (time/1000));
	}

}
