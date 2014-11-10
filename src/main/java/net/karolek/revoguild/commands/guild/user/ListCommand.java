package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.tablist.update.RankList;
import net.karolek.revoguild.tablist.update.RankList.Data;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

public class ListCommand extends SubCommand {

    public ListCommand() {
        super("lista", "lista wszystkich gildii", "", "revoguild.list", "list");
    }

    @Override
    public boolean onCommand(Player p, String[] args) {
        Util.sendMsg(p, Lang.LIST_GUILD_HEADER);
        for (Data<Guild> g : RankList.getTopGuilds())
            Util.sendMsg(p, Lang.parse(Lang.LIST_GUILD_ELEMENT, g.getKey()));
        Util.sendMsg(p, Lang.LIST_GUILD_FOOTER);
        return true;
    }
}
