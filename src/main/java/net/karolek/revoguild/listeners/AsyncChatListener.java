package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Alliance;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.AllianceManager;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AsyncChatListener implements Listener {

	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {

		String format = e.getFormat();

		Player p = e.getPlayer();
		User u = UserManager.getUser(p);

		Guild g = GuildManager.getGuild(p);

		String guildTag = g != null ? Lang.parse(Config.CHAT_FORMAT_TAG, g) : "";
		String rank = Config.CHAT_FORMAT_RANK.replace("{RANK}", Integer.toString(u.getPoints().get()));

		String local = StringUtils.replace(format, "{GUILD}", guildTag);
		local = local.replace("{RANK}", Util.fixColor(rank));
		e.setFormat(local);
		localChat(e);

	}

	private void localChat(AsyncPlayerChatEvent e) {
		if (!Config.CHAT_LOCAL_ENABLED)
			return;
		Player p = e.getPlayer();
		String msg = e.getMessage();
		Guild g = GuildManager.getGuild(p);
		if (msg.startsWith(Config.CHAT_LOCAL_CHAR + Config.CHAT_LOCAL_CHAR)) {
			if (g == null) {
				Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);
				e.setCancelled(true);
				return;
			}
			String format = Config.CHAT_LOCAL_FORMAT_ALLIANCE;
			format = Lang.parse(format, g);
			format = Lang.parse(format, p);
			format = format.replace("{MESSAGE}", ChatColor.stripColor(Util.fixColor(msg)));
			format = format.replaceFirst(Config.CHAT_LOCAL_CHAR + Config.CHAT_LOCAL_CHAR, "");
			for (Alliance a : AllianceManager.getGuildAlliances(g)) {
				Guild o = (a.getGuild1().equals(g)) ? a.getGuild2() : a.getGuild1();
				Util.sendMsg(o.getOnlineMembers(), format);
			}
			Util.sendMsg(g.getOnlineMembers(), format);
			e.setCancelled(true);
		} else if (msg.startsWith(Config.CHAT_LOCAL_CHAR)) {
			if (g == null) {
				Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);
				e.setCancelled(true);
				return;
			}
			String format = Config.CHAT_LOCAL_FORMAT_GUILD;
			format = Lang.parse(format, p);
			format = format.replace("{MESSAGE}", ChatColor.stripColor(Util.fixColor(msg)));
			format = format.replaceFirst(Config.CHAT_LOCAL_CHAR, "");
			Util.sendMsg(g.getOnlineMembers(), format);
			e.setCancelled(true);
		}
	}

}
