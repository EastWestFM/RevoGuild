package net.karolek.revoguild.commands;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class RevoGuildCommand extends SubCommand {

    public RevoGuildCommand() {
        super("revoguild", "main RevoGuild command", "/revoguild", "revoguild.main");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        Util.sendMsg(p, "&7&m--------&r &cRevoGUILD &7&m--------&r");
        Util.sendMsg(p, " &8» &7Wersja: &c" + GuildPlugin.getPlugin().getDescription().getVersion());
        Util.sendMsg(p, " &8» &7Strona WWW: &chttp://karolek.net/");
        Util.sendMsg(p, " &8» &7Autor: &cKarolek &7(gg: &o37846242&7; skype: &opojebanyjestes&7)");
        Util.sendMsg(p, "&7Nie usuwaj tej notki, szanuj czyjas prace, to bardzo mile! ;)");
        return true;
    }
}
