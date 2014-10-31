package net.karolek.revoguild.commands.guild.user;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.commands.SubCommand;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DeleteCommand extends SubCommand {

	public DeleteCommand() {
		super("usun", "usuwanie gildii", "", "revoguild.delete", "delete");
	}

	@Override
	public boolean onCommand(Player p, String[] args) {

		final Guild g = GuildManager.getGuild(p);

		if (g == null)
			return Util.sendMsg(p, Lang.ERROR_DONT_HAVE_GUILD);

		if (!g.isOwner(UserManager.getUser(p)))
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

		GuildManager.removeGuild(g);
		return Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_DELETED, g));

	}

}
