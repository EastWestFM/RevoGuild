package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.CombatManager;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.NameTagManager;
import net.karolek.revoguild.managers.TabManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinQuitListener implements Listener {

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		NameTagManager.getNameTag().initPlayer(p);
		User u = UserManager.getUser(p);
		if (u == null)
			u = UserManager.createUser(p);
		u.setJoinTime(System.currentTimeMillis());
		UserManager.initUser(p);
		if (Config.TABLIST_ENABLED)
			TabManager.createTab(p);
		CombatManager.createPlayer(p);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		escape(e.getPlayer());
		leave(e.getPlayer());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		escape(e.getPlayer());
		leave(e.getPlayer());
	}

	private void leave(Player p) {
		User u = UserManager.getUser(p);
		if (u == null)
			throw new RuntimeException("User not exists!");

		long time = System.currentTimeMillis() - u.getJoinTime();
		u.addTimePlay((int) (time / 1000));
	}
	
	private void escape(Player p) {
		if (!CombatManager.isInFight(p))
			return;

		User u = UserManager.getUser(p);

		Guild pGuild = GuildManager.getGuild(p);
		
		String pGuildTag = pGuild != null ? Lang.parse(Config.CHAT_FORMAT_TAGDEATHMSG, pGuild) : "";
		
		String mes = Config.ESCAPE_BROADCAST;
		mes = mes.replace("{PGUILD}", pGuildTag);
		mes = mes.replace("{PLAYER}", p.getName());
		
		u.addDeath();
		u.removePoints(Config.ESCAPE_POINTSREMOVE);
		p.setHealth(0D);
		
		TabThread.restart();

		Util.sendMsg(Util.getOnlinePlayers(), mes);
	
	}

}
