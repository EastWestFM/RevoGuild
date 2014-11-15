package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.AllianceManager;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class AllianceCommand extends SubCommand {

    public AllianceCommand() {
        super("sojusz", "zarzadzanie sojuszami gildii", "/g sojusz <tag/nazwa>", "revoguild.alliance", "alliance");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        if (args.length != 1)
            return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

        Guild g = GuildManager.getGuild(p);

        if (g == null)
            return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

        if (!g.isOwner(UserManager.getUser(p)))
            return Util.sendMsg(p, Lang.ERROR_NOT_LEADER);

        Guild o = GuildManager.getGuild(args[0]);

        if (o == null)
            return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

        if (AllianceManager.hasAlliance(g, o)) {
            AllianceManager.removeAlliance(g, o);
            return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_ALLIANCE_DELETED, g, o));
        }

        if(AllianceManager.getGuildAlliances(g).size() >= Config.ALLIANCES_MAXCOUNT)
            return Util.sendMsg(p, Lang.ERROR_ALLIANCES_MAXCOUNT);

        if (AllianceManager.getInvites().contains(o.getTag() + ":" + g.getTag())) {

            List<ItemStack> items = ItemUtil.getItems(p.hasPermission("revoguild.vip") ? Config.COST_ALLIANCE_VIP : Config.COST_ALLIANCE_NORMAL, 1);

            if(!ItemUtil.checkAndRemove(items, p))
                return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS.replace("{ITEMS}", ItemUtil.getItems(items)));

            AllianceManager.getInvites().remove(o.getTag() + ":" + g.getTag());
            AllianceManager.createAlliance(g, o);
            return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_ALLIANCE_CREATED, g, o));
        }

        OfflinePlayer owner = o.getOwner().get().getOfflinePlayer();

        if (!owner.isOnline())
            return Util.sendMsg(p, Lang.ERROR_OWNER_NOT_ONLINE);

        AllianceManager.getInvites().add(g.getTag() + ":" + o.getTag());

        Util.sendMsg(owner.getPlayer(), Lang.parse(Lang.INFO_ALLY_NEW, g));
        return Util.sendMsg(p, Lang.parse(Lang.INFO_ALLY_SEND, o));

    }
}
