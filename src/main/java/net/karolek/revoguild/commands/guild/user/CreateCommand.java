package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class CreateCommand extends SubCommand {

    public CreateCommand() {
        super("zaloz", "tworzenie gildii", "/g zaloz  <tag> <nazwa>", "revoguild.create", "nowa", "create");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 2)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        String tag = args[0];
        String name = args[1];

        if (GuildManager.getGuild(p) != null)
            return Util.sendMsg(p, Lang.ERROR_HAVE_GUILD);

        if ((tag.length() < 2 || tag.length() > 4) || (name.length() < 2 || name.length() > 32))
            return Util.sendMsg(p, Lang.ERROR_TAG_AND_NAME_FORMAT);

        if (!Util.isAlphaNumeric(tag) || !Util.isAlphaNumeric(name))
            return Util.sendMsg(p, Lang.ERROR_TAG_AND_NAME_ALFANUM);

        if (GuildManager.getGuild(tag) != null || GuildManager.getGuild(name) != null)
            return Util.sendMsg(p, Lang.ERROR_GUILD_EXISTS);

        if (!GuildManager.canCreateGuild(p.getLocation()))
            return Util.sendMsg(p, Lang.ERROR_NEARBY_IS_GUILD);

        List<ItemStack> items = ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_CREATE_VIP : Config.COST_CREATE_NORMAL, 1);

        if (!ItemUtil.checkAndRemove(items, p))
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

        Guild g = GuildManager.createGuild(tag, name, p);

        return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_CREATED, g));
    }

}
