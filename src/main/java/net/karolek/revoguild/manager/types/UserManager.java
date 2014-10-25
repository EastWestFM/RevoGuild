package net.karolek.revoguild.manager.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.utils.Logger;

public class UserManager implements IManager {

	@Getter
	private Set<User>	users	= null;

	public User getUser(OfflinePlayer p) {
		for (User u : users)
			if ((u.getUuid() == null && u.getName().equalsIgnoreCase(p.getName())) || p.getUniqueId().equals(u.getUuid()))
				return u;
		return null;
	}

	public User getUser(String s) {
		for (User u : users) {
			//System.out.println(u.toString() + "==" + s);
			if (u.toString().equals(s)) { return u; }
		}
		return null;
	}

	public User createUser(Player p) {
		User u = new User(p);
		u.insert();
		users.add(u);
		return u;
	}

	@Override
	public void enable() throws Exception {
		users = new HashSet<User>();

		ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}users`");
		try {
			while (rs.next()) {
				User u = new User(rs);
				users.add(u);
			}
			Logger.info("Loaded " + users.size() + " users!");
		} catch (SQLException e) {
			Logger.warning("An error occurred while loading users!", "Error: " + e.getMessage());
			Logger.exception(e);
		}
	}

	@Override
	public void disable() {
		users.clear();
		users = null;
	}

}
