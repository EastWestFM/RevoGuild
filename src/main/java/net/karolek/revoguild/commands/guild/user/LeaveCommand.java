package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.NameTagManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

	public LeaveCommand() {
		super("opusc", "opuszczanie gildii", "", "revoguild.leave", "leave");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		User u = UserManager.getUser(p);

		if (g.isOwner(u))
			return Util.sendMsg(p, Lang.ERROR_OWNER_CANT_LEAVE_GUILD);

		if (g.isLeader(u)) {
			g.setOwner(g.getOwner());
			g.update(false);
		}

		g.removeMember(u);

		NameTagManager.leaveFromGuild(g, p);

		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_LEAVED, g, p));
	}
}
