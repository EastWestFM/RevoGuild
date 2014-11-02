package net.karolek.revoguild.commands.ranking;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.tablist.update.RankList;
import net.karolek.revoguild.tablist.update.RankList.Data;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

public class TopCommand extends SubCommand {

	public TopCommand() {
		super("top", "top10 graczy", "", "revoguild.top", "topka", "top10");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
				Util.sendMsg(p, Lang.LIST_RANKING_HEADER);

		int i = 1;
		for (Data<User> u : RankList.getTopPlayers()) {
			if (i > 10)
				break;

			Util.sendMsg(p, Lang.parse(Lang.LIST_RANKING_ELEMENT, u.getKey()).replace("{POS}", Integer.toString(i)));

			i++;
		}

		return Util.sendMsg(p, Lang.LIST_RANKING_FOOTER);

	}
}
