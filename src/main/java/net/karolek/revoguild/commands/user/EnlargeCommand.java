package net.karolek.revoguild.commands.user;

import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.ItemUtil;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class EnlargeCommand extends SubCommand {

	public EnlargeCommand() {
		super("powieksz", "powiekszanie terenu gildii", "", "rg.cmd.user.enlarge", "resize", "enlarge");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {
		Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(p.getUniqueId()))
			return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);
		
		String algorithm = Config.ENLARGE_ALGORITHM;
		algorithm = algorithm.replace("{CUBOID_SIZE}", Integer.toString(g.getCuboid().getSize()));
		
		ScriptEngineManager manager= new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		int modifier = 1;
		try {
			modifier = ((Double) engine.eval(algorithm)).intValue();
		} catch (ScriptException e) {
			Logger.exception(e);
		}

		List<ItemStack> items = ItemUtil.getItems(Config.COST_ENLARGE, modifier);
		
		if (!ItemUtil.checkItems(items, p))
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_ITEMS);

		if (!g.addSize())
			return Util.sendMsg(p, Lang.ERROR_MAX_SIZE);

		ItemUtil.removeItems(items, p);
		
		return Util.sendMsg(p, Lang.INFO_RESIZED);
	}
}
