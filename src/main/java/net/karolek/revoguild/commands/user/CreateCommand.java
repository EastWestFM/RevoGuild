package net.karolek.revoguild.commands.user;

import java.util.List;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Util;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class CreateCommand extends SubCommand {

	public CreateCommand() {
		super("zaloz", "tworzenie gildii", "<tag> <nazwa>", "rg.cmd.user.create", "nowa", "create");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		if (args.length != 2)
			return Util.sendMsg(p, Lang.parse(Lang.CMD_CORRECT_USAGE, this));

		String tag = args[0];
		String name = args[1];

		if (Manager.GUILD.getGuild(p) != null)
			return Util.sendMsg(p, Lang.ERROR_HAVE_GUILD);

		if ((tag.length() < 2 || tag.length() > 4) || (name.length() < 2 || name.length() > 32))
			return Util.sendMsg(p, Lang.ERROR_TAG_AND_NAME_FORMAT);

		if (!Util.isAlphaNumeric(tag) || !Util.isAlphaNumeric(name))
			return Util.sendMsg(p, Lang.ERROR_TAG_AND_NAME_ALFANUM);

		if (Manager.GUILD.getGuild(tag) != null || Manager.GUILD.getGuild(name) != null)
			return Util.sendMsg(p, Lang.ERROR_GUILD_EXISTS);

		if (!Manager.GUILD.canCreateGuild(p.getLocation()))
			return Util.sendMsg(p, Lang.ERROR_NEARBY_IS_GUILD);

		List<ItemStack> items = ItemUtil.getItems(Config.COST_CREATE, 1);

		if (!ItemUtil.checkAndRemove(items, p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		Guild g = Manager.GUILD.createGuild(tag, name, p);

		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_CREATED, g));	}

}
