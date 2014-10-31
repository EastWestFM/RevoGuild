package net.karolek.revoguild.commands.guild.admin;

import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.data.TabScheme;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class ReloadCommand extends SubCommand {

	public ReloadCommand() {
		super("reload", "przeladowanie plikow konfiguracyjnych", "", "revoguild.admin.reload", "przeladuj");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Config.reloadConfig();
		Lang.reloadLang();
		TabScheme.reloadTablist();
		TabThread.restart();
		return Util.sendMsg(p, Lang.INFO_RELOADED);
	}
}
