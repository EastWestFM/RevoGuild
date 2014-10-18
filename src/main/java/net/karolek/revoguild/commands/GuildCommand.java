package net.karolek.revoguild.commands;

import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class GuildCommand extends SubCommand {

	public GuildCommand() {
		super("gildia", "glowna komenda systemu gildii", "", "rg.cmd.user", "gildie", "guild", "g");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		return Util.sendMsg(p, "&cTest c:");
	}

}
