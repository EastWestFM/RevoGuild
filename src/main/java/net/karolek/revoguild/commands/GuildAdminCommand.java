package net.karolek.revoguild.commands;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import net.karolek.revoguild.commands.admin.*;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class GuildAdminCommand extends SubCommand {

	@Getter
	private static final Set<SubCommand>	subCommands	= new HashSet<SubCommand>();

	public GuildAdminCommand() {
		super("gildiaadmina", "glowna komenda adminsitratora systemu gildii", "", "rg.cmd.admin", "gildieadmin", "guildadmin", "ga");
		subCommands.add(new TeleportCommand());
		subCommands.add(new ReloadCommand());
		subCommands.add(new DeleteCommand());
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		if (args.length == 0)
			return Util.sendMsg(p, Lang.CMD_MAIN_ADMIN_HELP);

		String name = args[0];

		SubCommand sc = getSub(name);

		if (sc == null)
			return Util.sendMsg(p, Lang.CMD_MAIN_ADMIN_HELP);

		return sc.onCommand(p, Arrays.copyOfRange(args, 1, args.length));

	}

	private SubCommand getSub(String sub) {
		for (SubCommand sc : subCommands)
			if (sc.getName().equalsIgnoreCase(sub) || sc.getAliases().contains(sub))
				return sc;
		return null;
	}

}
