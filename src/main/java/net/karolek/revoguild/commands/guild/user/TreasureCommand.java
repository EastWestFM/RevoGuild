package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class TreasureCommand extends SubCommand {

	public TreasureCommand() {
		super("skarbiec", "skarbiec gildii", "[dodaj <gracz> / usun <gracz> / lista]", "revoguild.treasure", "skrzynia", "treasure");
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(Player p, String[] args) {
		
		Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		User pU = UserManager.getUser(p);
		
		if (args.length == 0) {
			if (g.isTreasureUser(pU)) {
				if(Config.TREASURE_OPENONLYONGUILD) {
					Guild o = GuildManager.getGuild(p.getLocation());
					if (o == null || !o.equals(g))
						return Util.sendMsg(p, Lang.ERROR_CANT_OPEN_TREASURE_OUTSIDE_CUBOID);
				}
				g.openTreasure(p);
				return Util.sendMsg(p, Lang.INFO_TREASURE_OPENED);
			}
			return Util.sendMsg(p, Lang.ERROR_CANT_OPEN_TREASURE);
		} else if (args.length >= 1) {
			if (!g.isOwner(pU))
				return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);
			OfflinePlayer op = null;
			User u = null;
			switch (args[0]) {
				case "dodaj":
					if (args.length != 2)
						return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

					op = Bukkit.getOfflinePlayer(args[1]);
					u = UserManager.getUser(op);

					if (!g.isMember(u))
						return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);

					if (g.isTreasureUser(u))
						return Util.sendMsg(p, Lang.ERROR_PLAYER_IS_TREASURE_USER);

					g.addTreasureUser(u);
					if (op.isOnline())
						Util.sendMsg(op.getPlayer(), Lang.parse(Lang.INFO_TREASURE_USER_ADD_INFO, p));
					return Util.sendMsg(p, Lang.parse(Lang.INFO_TREASURE_USER_ADD, op));
				case "usun":
					if (args.length != 2)
						return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

					op = Bukkit.getOfflinePlayer(args[1]);
					u = UserManager.getUser(op);

					if (!g.isMember(u))
						return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_MEMBER);

					if (!g.isTreasureUser(u))
						return Util.sendMsg(p, Lang.ERROR_PLAYER_ISNT_TREASURE_USER);

					g.removeTreasureUser(u);
					if (op.isOnline())
						Util.sendMsg(op.getPlayer(), Lang.parse(Lang.INFO_TREASURE_USER_REMOVE_INFO, p));
					return Util.sendMsg(p, Lang.parse(Lang.INFO_TREASURE_USER_REMOVE, op));
				case "lista":
					return Util.sendMsg(p, Lang.INFO_TREASURE_USERS.replace("{USERS}", Lang.getTreasureUsers(g)));
				default:
					return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));
			}
		}

		g.openTreasure(p);
		return true;
	}
}
