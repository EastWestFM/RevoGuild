package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.NameTagManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class JoinCommand extends SubCommand {

    public JoinCommand() {
        super("dolacz", "dolaczanie do gildii", "<tag/nazwa>", "revoguild.join", "join");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {

        if (args.length != 1)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        if (GuildManager.getGuild(p) != null)
            return Util.sendMsg(p, Lang.ERROR_HAVE_GUILD);

        Guild g = GuildManager.getGuild(args[0]);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

        List<ItemStack> items = ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_JOIN_VIP : Config.COST_JOIN_NORMAL, 1);

        if (!ItemUtil.checkItems(items, p))
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

        if (!g.addMember(UserManager.getUser(p)))
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_INVITE);

        ItemUtil.removeItems(items, p);

        Util.sendMsg(p, Lang.parse(Lang.INFO_JOINED, g));

        NameTagManager.joinToGuild(g, p);


        return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_JOINED, g, p));


    }

}
