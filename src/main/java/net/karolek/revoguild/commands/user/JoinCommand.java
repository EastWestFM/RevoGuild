package net.karolek.revoguild.commands.user;

import java.util.List;









import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class JoinCommand extends SubCommand {

	public JoinCommand() {
		super("dolacz", "dolaczanie do gildii", "<tag/nazwa>", "rg.cmd.user.join", "join");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		if (args.length != 1)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		if (Manager.GUILD.getGuild(p) != null)
			return Util.sendMsg(p, Lang.ERROR_HAVE_GUILD);

		Guild g = Manager.GUILD.getGuild(args[0]);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_CANT_FIND_GUILD);

		List<ItemStack> items = ItemUtil.getItems(Config.COST_JOIN, 1);

		if (!ItemUtil.checkItems(items, p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		if (!g.addMember(p.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_INVITE);

		ItemUtil.removeItems(items, p);
				
		Util.sendMsg(p, Lang.parse(Lang.INFO_JOINED, g));
		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_JOINED, g, p));


	}

}
