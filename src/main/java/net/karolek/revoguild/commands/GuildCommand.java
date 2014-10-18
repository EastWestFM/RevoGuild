package net.karolek.revoguild.commands;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class GuildCommand extends SubCommand {

	@Getter
	private static final Set<SubCommand> subCommands = new HashSet<SubCommand>();
	
	public GuildCommand() {
		super("gildia", "glowna komenda systemu gildii", "", "rg.cmd.user", "gildie", "guild", "g");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		return Util.sendMsg(p, "&cTest c:");
	}

}
