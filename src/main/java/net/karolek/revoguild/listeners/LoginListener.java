package net.karolek.revoguild.listeners;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.tablist.Tab;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class LoginListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onLogin(PlayerLoginEvent e) {
		final Player p = e.getPlayer();
		if (p == null || e.getResult() != PlayerLoginEvent.Result.ALLOWED)
			return;

		new BukkitRunnable() {

			@Override
			public void run() {
				Tab tab = Manager.TAB.createTab(p);
				Manager.TAB.initTablist(tab);
				tab.send();
			}

		}.runTask(GuildPlugin.getPlugin());

	}

}
