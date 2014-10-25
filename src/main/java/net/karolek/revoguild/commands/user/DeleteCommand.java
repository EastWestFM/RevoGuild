package net.karolek.revoguild.commands.user;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteCommand extends SubCommand {

	public DeleteCommand() {
		super("usun", "usuwanie gildii", "", "rg.cmd.user.delete", "delete");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		final Guild g = Manager.GUILD.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(Manager.USER.getUser(p)))
			return Util.sendMsg(p, Lang.ERROR_NOT_OWNER);

		if (!g.isPreDeleted()) {
			g.setPreDeleted(true);

			Util.sendMsg(p, Lang.INFO_CONFIRM_DELETE);

			new BukkitRunnable() {

				@Override
				public void run() {

					if (g != null && g.isPreDeleted())
						g.setPreDeleted(false);

				}

			}.runTaskLater(GuildPlugin.getPlugin(), 20 * 10);

			return true;
		}

		Manager.GUILD.removeGuild(g);
		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_DELETED, g));

	}

}
