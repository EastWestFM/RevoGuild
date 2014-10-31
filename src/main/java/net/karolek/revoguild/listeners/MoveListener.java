package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

	@EventHandler
	public void onPlayerMove(final PlayerMoveEvent e) {
		int xfrom = e.getFrom().getBlockX();
		int zfrom = e.getFrom().getBlockZ();
		int yfrom = e.getFrom().getBlockY();
		int xto = e.getTo().getBlockX();
		int yto = e.getTo().getBlockY();
		int zto = e.getTo().getBlockZ();

		if ((xfrom != xto) || (zfrom != zto) || (yfrom != yto)) {

			Player p = e.getPlayer();
			Guild from = GuildManager.getGuild(e.getFrom());
			Guild to = GuildManager.getGuild(e.getTo());
			if ((from == null) && (to != null)) {
				Util.sendMsg(p, Lang.parse(Lang.INFO_MOVE_IN, to));
			} else if ((from != null) && (to == null)) {
				Util.sendMsg(p, Lang.parse(Lang.INFO_MOVE_OUT, from));
			}

		}

	}

}
