package net.karolek.revoguild.commands;

import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.CombatManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class CombatCommand extends SubCommand {

    public CombatCommand() {
        super("combat", "sprawdzanie czasu walki", "/combat", "revoguild.combat", "ct", "tag", "walka", "pvp", "logout");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        return Util.sendMsg(p, Lang.INFO_FIGHT_TIME.replace("{TIME}", CombatManager.getTime(p)));
    }
}