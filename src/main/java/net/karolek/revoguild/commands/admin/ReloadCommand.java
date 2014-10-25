package net.karolek.revoguild.commands.admin;

import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

	public ReloadCommand() {
		super("reload", "przeladowanie plikow konfiguracyjnych", "", "rg.cmd.admin.reload", "przeladuj");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Config.reloadConfig();
		Lang.reloadLang();
		return Util.sendMsg(p, Lang.INFO_RELOADED);
	}
}
