package net.karolek.revoguild.manager.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.utils.Logger;

public class GuildManager implements IManager {

	@Getter
	private Set<Guild>	guilds	= null;

	public Guild createGuild(String tag, String name, Player owner) {
		Guild g = new Guild(tag, name, owner);
		g.insert();
		g.addInvite(owner.getUniqueId());
		g.addMember(owner.getUniqueId());
		guilds.add(g);
		return g;
	}

	public void removeGuild(Guild g) {
		g.delete();
		guilds.remove(g);
	}

	public Guild getGuild(String tag) {
		for (Guild g : guilds)
			if ((g.getTag().equalsIgnoreCase(tag)) || (g.getName().equalsIgnoreCase(tag)))
				return g;
		return null;
	}

	public Guild getGuild(Location loc) {
		for (Guild g : guilds)
			if (g.getCuboid().isInCuboid(loc))
				return g;
		return null;
	}

	public Guild getGuild(Player p) {
		for (Guild g : guilds)
			if (g.isMember(p.getUniqueId()))
				return g;
		return null;
	}

	public boolean canCreateGuild(Location loc) {
		int mindistance = Config.SIZE_MAX * 2 + Config.SIZE_BETWEEN;
		for (Guild g : guilds)
			if ((Math.abs(g.getCuboid().getCenterX() - loc.getBlockX()) <= mindistance) && (Math.abs(g.getCuboid().getCenterZ() - loc.getBlockZ()) <= mindistance))
				return false;
		return true;
	}

	@Override
	public void enable() throws Exception {
		guilds = new HashSet<Guild>();
		ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}guilds`");
		try {
			while (rs.next()) {
				Guild g = new Guild(rs);
				guilds.add(g);
			}
			Logger.info("Loaded " + guilds.size() + " guilds!");
		} catch (SQLException e) {
			Logger.warning("An error occurred while loading guilds!", "Error: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void disable() {
		guilds.clear();
		guilds = null;
	}

}
