package net.karolek.revoguild.commands.guild;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.commands.guild.user.AllianceCommand;
import net.karolek.revoguild.commands.guild.user.CreateCommand;
import net.karolek.revoguild.commands.guild.user.DeleteCommand;
import net.karolek.revoguild.commands.guild.user.EnlargeCommand;
import net.karolek.revoguild.commands.guild.user.HomeCommand;
import net.karolek.revoguild.commands.guild.user.InfoCommand;
import net.karolek.revoguild.commands.guild.user.InviteCommand;
import net.karolek.revoguild.commands.guild.user.JoinCommand;
import net.karolek.revoguild.commands.guild.user.KickCommand;
import net.karolek.revoguild.commands.guild.user.LeaderCommand;
import net.karolek.revoguild.commands.guild.user.LeaveCommand;
import net.karolek.revoguild.commands.guild.user.ListCommand;
import net.karolek.revoguild.commands.guild.user.OwnerCommand;
import net.karolek.revoguild.commands.guild.user.ProlongCommand;
import net.karolek.revoguild.commands.guild.user.PvpCommand;
import net.karolek.revoguild.commands.guild.user.SetHomeCommand;
import net.karolek.revoguild.commands.guild.user.TreasureCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.CommandManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class GuildCommand extends SubCommand {

	@Getter
	private static final Set<SubCommand>	subCommands	= new HashSet<SubCommand>();

	public GuildCommand() {
		super("gildia", "glowna komenda systemu gildii", "", "revoguild.main", "gildie", "guild", "g");
		subCommands.add(new CreateCommand());
		subCommands.add(new DeleteCommand());
		subCommands.add(new HomeCommand());
		subCommands.add(new InfoCommand());
		subCommands.add(new InviteCommand());
		subCommands.add(new JoinCommand());
		subCommands.add(new KickCommand());
		subCommands.add(new LeaderCommand());
		subCommands.add(new OwnerCommand());
		subCommands.add(new LeaveCommand());
		subCommands.add(new ListCommand());
		subCommands.add(new PvpCommand());
		subCommands.add(new EnlargeCommand());
		subCommands.add(new SetHomeCommand());
		subCommands.add(new ProlongCommand());
		subCommands.add(new AllianceCommand());

		if (Config.TREASURE_ENABLED)
			subCommands.add(new TreasureCommand());

		for (SubCommand sc : subCommands)
			CommandManager.register(sc);
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		if (args.length == 0)
			return Util.sendMsg(p, Lang.CMD_MAIN_HELP);

		String name = args[0];

		SubCommand sc = getSub(name);

		if (sc == null)
			return Util.sendMsg(p, Lang.CMD_MAIN_HELP);
		
		if (!p.hasPermission(sc.getPermission()))
			return Util.sendMsg(p, "&cYou don't have permissions to run that command! &7(" + sc.getPermission() + ")");

		return sc.onCommand(p, Arrays.copyOfRange(args, 1, args.length));

	}

	private SubCommand getSub(String sub) {
		for (SubCommand sc : subCommands)
			if (sc.getName().equalsIgnoreCase(sub) || sc.getAliases().contains(sub))
				return sc;
		return null;
	}

}
