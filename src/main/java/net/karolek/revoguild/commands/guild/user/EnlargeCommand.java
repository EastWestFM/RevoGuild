package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EnlargeCommand extends SubCommand {

    public EnlargeCommand() {
        super("powieksz", "powiekszanie terenu gildii", "/g powieksz", "revoguild.enlarge", "resize", "enlarge");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        Guild g = GuildManager.getGuild(p);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

        if (!g.isOwner(UserManager.getUser(p)))
            return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

        String algorithm = Config.ALGORITHM_ENLARGE;
        algorithm = algorithm.replace("{CUBOID_SIZE}", Integer.toString(g.getCuboid().getSize()));

        int modifier = Util.calculate(algorithm);

        List<ItemStack> items = ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_ENLARGE_VIP : Config.COST_ENLARGE_NORMAL, modifier);

        if (!ItemUtil.checkItems(items, p))
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS.replace("{ITEMS}", ItemUtil.getItems(items)));

        if (!g.addSize())
            return Util.sendMsg(p, Lang.ERROR_MAX_SIZE);

        ItemUtil.removeItems(items, p);

        return Util.sendMsg(p, Lang.INFO_RESIZED);
    }
}
