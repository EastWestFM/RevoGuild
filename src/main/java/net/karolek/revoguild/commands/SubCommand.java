package net.karolek.revoguild.commands;

import lombok.Getter;
import net.karolek.revoguild.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

@Getter
public abstract class SubCommand extends Command {

    private final String name;
    private final String usage;
    private final String desc;
    private final String permission;

    public SubCommand(String name, String desc, String usage, String permission, String... aliases) {
        super(name, desc, usage, Arrays.asList(aliases));

        this.name = name;
        this.usage = usage;
        this.desc = desc;
        this.permission = permission;

    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player))
            return Util.sendMsg(sender, "&cYou must be a player to run that command!");

        Player p = (Player) sender;


        if (!p.hasPermission(this.permission))
            return Util.sendMsg(p, "&cYou don't have permissions to run that command! &7(" + this.permission + ")");

        return onCommand(p, args);
    }

    public abstract boolean onCommand(Player p, String[] args);

}
