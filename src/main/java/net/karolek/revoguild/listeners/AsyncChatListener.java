package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		String format = e.getFormat();

		Player p = e.getPlayer();
		User u = Manager.USER.getUser(p);

		Guild g = Manager.GUILD.getGuild(p);

		String guildTag = g != null ? Lang.parse(Config.CHAT_FORMAT_TAG, g) : "";
		String rank = Config.CHAT_FORMAT_RANK.replace("{RANK}", Integer.toString(u.getPoints()));

		String local = StringUtils.replace(format, "{GUILD}", guildTag);
		local = local.replace("{RANK}", Util.fixColor(rank));
		e.setFormat(local);

	}

}
