package net.karolek.revoguild.commands.user;

import net.karolek.revoguild.base.Guild;
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

		if (g.isOwner(p.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_OWNER_CANT_LEAVE_GUILD);

		if (g.isLeader(p.getUniqueId())) {
			g.setOwner(g.getOwner());
			g.update(false);
		}

		g.removeMember(p.getUniqueId());

		Manager.TAG.getNameTag().leaveFromGuild(g, p);

		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_LEAVED, g, p));
	}
}
