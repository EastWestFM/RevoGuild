package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.NameTagManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class KickCommand extends SubCommand {

	public KickCommand() {
		super("wyrzuc", "wyrzucanie gracza z gildii", "<gracz>", "revoguild.kick", "kick");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isLeader(UserManager.getUser(p)))
			return Util.sendMsg(p, Lang.ERROR_NOT_LEADER);

		@SuppressWarnings("deprecation")
		OfflinePlayer op = Bukkit.getOfflinePlayer(args[0]);
		User u  = UserManager.getUser(op);
		if (g.isLeader(u))
			return Util.sendMsg(p, Lang.ERROR_CANT_KICK_LEADER_OR_OWNER);

		if (!g.removeMember(u))
			return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);

		NameTagManager.leaveFromGuild(g, op);

		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_KICKED, g, op));
	}
}
