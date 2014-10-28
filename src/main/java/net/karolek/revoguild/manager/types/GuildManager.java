package net.karolek.revoguild.manager.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.tablist.TabThread;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.SpaceUtil;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GuildManager implements IManager {

	@Getter
	private Map<String, Guild>	guilds	= null;

	public Guild createGuild(String tag, String name, Player owner) {
		Guild g = new Guild(tag, name, owner);
		g.insert();
		User u = Manager.USER.getUser(owner);
		g.addInvite(u);
		g.addMember(u);
		g.addTreasureUser(u);
		guilds.put(g.getTag().toUpperCase(), g);
		Manager.TAG.getNameTag().createGuild(g, owner);
		setGuildRoom(g);
		owner.teleport(g.getCuboid().getCenter());
		g.setHome(g.getCuboid().getCenter());
		g.update(false);
		Location center = g.getCuboid().getCenter();
		center.setY(100);
		TabThread.restart();

		return g;
	}

	public void removeGuild(Guild g) {
		g.delete();
		Location l = g.getCuboid().getCenter();
		l.setY(62);
		l.getBlock().setType(Material.AIR);
		l.setY(61);
		l.getBlock().setType(Material.AIR);
		Manager.TAG.getNameTag().removeGuild(g);
		TabThread.restart();

		guilds.remove(g.getTag().toUpperCase());
		//if (!guilds.remove(g)) {
		//	for (int i = 0; i < guilds.size(); i++) {
		//		if (guilds.get(i).equals(g)) {
		//			guilds.remove(i);
		//			break;
		//		}
		//	}
		//}
	}

	public Guild getGuild(String tag) {
		for (Guild g : guilds.values())
			if ((g.getTag().equalsIgnoreCase(tag)) || (g.getName().equalsIgnoreCase(tag)))
				return g;
		return null;
	}

	public Guild getGuild(Location loc) {
		for (Guild g : guilds.values())
			if (g.getCuboid().isInCuboid(loc))
				return g;
		return null;
	}

	public Guild getGuild(Player p) {
		User u = Manager.USER.getUser(p);
		for (Guild g : guilds.values())
			if (g.isMember(u))
				return g;
		return null;
	}

	public boolean canCreateGuild(Location loc) {
		int mindistance = Config.SIZE_MAX * 2 + Config.SIZE_BETWEEN;
		for (Guild g : guilds.values())
			if ((Math.abs(g.getCuboid().getCenterX() - loc.getBlockX()) <= mindistance) && (Math.abs(g.getCuboid().getCenterZ() - loc.getBlockZ()) <= mindistance))
				return false;
		return true;
	}

	private void setGuildRoom(Guild g) {
		Location center = g.getCuboid().getCenter();
		for (Location loc : SpaceUtil.getSquare(center, 4, 5))
			loc.getBlock().setType(Material.AIR);
		for (Location loc : SpaceUtil.getSquare(center, 4))
			loc.getBlock().setType(Material.OBSIDIAN);
		center.add(0, 1, 0);
		center.getBlock().setType(Material.DRAGON_EGG);
		for (Location loc : SpaceUtil.getCorners(center, 4, 4))
			loc.getBlock().setType(Material.OBSIDIAN);
		center.add(0, 4, 0);
		for (Location loc : SpaceUtil.getWalls(center, 4))
			loc.getBlock().setType(Material.OBSIDIAN);
		g.getCuboid().getCenter().getBlock().setType(Material.BEDROCK);;
	}

	@Override
	public void enable() throws Exception {
		guilds = new HashMap<String, Guild>();
		ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}guilds`");
		try {
			while (rs.next()) {
				Guild g = new Guild(rs);
				guilds.put(g.getTag().toUpperCase(), g);
			}
			Logger.info("Loaded " + guilds.size() + " guilds!");
		} catch (SQLException e) {
			Logger.warning("An error occurred while loading guilds!", "Error: " + e.getMessage());
			Logger.exception(e);
		}
	}

	@Override
	public void disable() {
		guilds.clear();
		guilds = null;
	}

}
