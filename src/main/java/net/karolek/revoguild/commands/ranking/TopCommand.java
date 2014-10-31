package net.karolek.revoguild.commands.ranking;

import java.util.Map;
import java.util.SortedMap;

import net.karolek.revoguild.base.User;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;

import com.google.common.base.Functions;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.Ordering;

public class TopCommand extends SubCommand {

	public TopCommand() {
		super("top", "top10 graczy", "", "revoguild.top", "topka", "top10");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Map<String, User> unsortedMap = UserManager.getUsers();
		SortedMap<String, User> sortedMap = ImmutableSortedMap.copyOf(unsortedMap, Ordering.natural().reverse().onResultOf(Functions.forMap(unsortedMap)).compound(Ordering.natural().reverse()));

		Util.sendMsg(p, Lang.LIST_RANKING_HEADER);

		int i = 1;
		for (User u : sortedMap.values()) {
			if (i > 10)
				break;

			Util.sendMsg(p, Lang.parse(Lang.LIST_RANKING_ELEMENT, u).replace("{POS}", Integer.toString(i)));

			i++;
		}

		return Util.sendMsg(p, Lang.LIST_RANKING_FOOTER);

	}
}
