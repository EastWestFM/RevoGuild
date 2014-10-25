package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();

		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return;

		String format = StringUtils.replace(e.getFormat(), "{GUILD}", Lang.parse(Config.CHAT_TAGFORMAT, g));

		e.setFormat(format);

	}

}
