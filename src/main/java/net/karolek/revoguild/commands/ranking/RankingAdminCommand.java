package net.karolek.revoguild.commands.ranking;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.commands.ranking.admin.ResetCommand;
import net.karolek.revoguild.commands.ranking.admin.SetCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class RankingAdminCommand extends SubCommand {

	@Getter
	private static final Set<SubCommand>	subCommands	= new HashSet<SubCommand>();

	public RankingAdminCommand() {
		super("rankingadmin", "glowna komenda adminsitratora rankingu", "", "revoguild.admin.main", "ra");
		subCommands.add(new ResetCommand());
		subCommands.add(new SetCommand());
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		if (args.length == 0)
			return Util.sendMsg(p, Lang.CMD_MAIN_ADMIN_RANKING_HELP);

		String name = args[0];

		SubCommand sc = getSub(name);

		if (sc == null)
			return Util.sendMsg(p, Lang.CMD_MAIN_ADMIN_RANKING_HELP);

		return sc.onCommand(p, Arrays.copyOfRange(args, 1, args.length));

	}

	private SubCommand getSub(String sub) {
		for (SubCommand sc : subCommands)
			if (sc.getName().equalsIgnoreCase(sub) || sc.getAliases().contains(sub))
				return sc;
		return null;
	}

}
