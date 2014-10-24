package net.karolek.revoguild.commands.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AllianceCommand extends SubCommand {

	public AllianceCommand() {
		super("sojusz", "zarzadzanie sojuszami gildii", "<tag/nazwa>", "rg.cmd.user.alliance", "alliance");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(p.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_NOT_LEADER);

		Guild o = Manager.GUILD.getGuild(args[0]);

		if (o == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

		if (Manager.ALLIANCE.hasAlliance(g, o)) {
			Manager.ALLIANCE.removeAlliance(g, o);
			return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_ALLIANCE_DELETED, g, o));
		}

		if (Manager.ALLIANCE.getInvites().contains(o.getTag() + ":" + g.getTag())) {
			Manager.ALLIANCE.getInvites().remove(o.getTag() + ":" + g.getTag());
			Manager.ALLIANCE.createAlliance(g, o);
			return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_ALLIANCE_CREATED, g, o));
		}

		OfflinePlayer owner = Bukkit.getOfflinePlayer(o.getOwner());

		if (!owner.isOnline())
			return Util.sendMsg(p, Lang.ERROR_OWNER_NOT_ONLINE);

		Manager.ALLIANCE.getInvites().add(g.getTag() + ":" + o.getTag());

		Util.sendMsg(owner.getPlayer(), Lang.parse(Lang.INFO_ALLY_NEW, g));
		return Util.sendMsg(p, Lang.parse(Lang.INFO_ALLY_SEND, o));

	}
}
