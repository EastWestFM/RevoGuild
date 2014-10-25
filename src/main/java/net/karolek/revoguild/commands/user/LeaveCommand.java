package net.karolek.revoguild.commands.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class LeaveCommand extends SubCommand {

	public LeaveCommand() {
		super("opusc", "opuszczanie gildii", "", "rg.cmd.user.leave", "leave");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		User u = Manager.USER.getUser(p);
		
		if (g.isOwner(u))
			return Util.sendMsg(p, Lang.ERROR_OWNER_CANT_LEAVE_GUILD);

		if (g.isLeader(u)) {
			g.setOwner(g.getOwner());
			g.update(false);
		}

		g.removeMember(u);

		Manager.TAG.getNameTag().leaveFromGuild(g, p);

		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_LEAVED, g, p));
	}
}
