package net.karolek.revoguild.commands;

import java.util.Arrays;

import lombok.Getter;
import net.karolek.revoguild.commands.guild.GuildAdminCommand;
import net.karolek.revoguild.commands.ranking.RankingAdminCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.CommandManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class AdminCommand extends SubCommand {

	@Getter
	private static final SubCommand	gaCmd	= new GuildAdminCommand();
	@Getter
	private static final SubCommand	raCmd	= new RankingAdminCommand();

	public AdminCommand() {
		super("admin", "komenda adminsitratora", "", "revoguild.admin", "adm", "administrator");
		CommandManager.register(gaCmd);
		CommandManager.register(raCmd);
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		if (args.length == 0)
			return Util.sendMsg(p, Lang.CMD_MAIN_ADMIN_HELP);

		String name = args[0];

		SubCommand sc = null;

		switch (name) {
			case "g":
			case "guild":
			case "gildie":
				sc = gaCmd;
				break;
			case "r":
			case "rank":
			case "ranking":
				sc = raCmd;
				break;
		}

		if (sc == null)
			return Util.sendMsg(p, Lang.CMD_MAIN_ADMIN_HELP);
		
		if (!p.hasPermission(sc.getPermission()))
			return Util.sendMsg(p, "&cYou don't have permissions to run that command! &7(" + sc.getPermission() + ")");

		return sc.onCommand(p, Arrays.copyOfRange(args, 1, args.length));

	}

}
