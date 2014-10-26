package net.karolek.revoguild.manager.types;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.utils.Logger;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class UserManager implements IManager {

	@Getter
	private Map<String, User>	users	= null;

	public User getUser(OfflinePlayer p) {
		for (User u : users.values())
			if (Config.USEUUID) {
				if (p.getUniqueId().equals(u.getUuid()))
					return u;
			} else {
				if (u.getUuid() == null && u.getName().equalsIgnoreCase(p.getName()))
					return u;
			}
		return null;
	}

	public User getUser(String s) {
		return users.get(s);
	}

	public User createUser(Player p) {
		User u = new User(p);
		u.insert();
		users.put(u.toString(), u);
		return u;
	}

	@Override
	public void enable() throws Exception {
		users = new HashMap<String, User>();

		ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}users`");
		try {
			while (rs.next()) {
				User u = new User(rs);
				users.put(u.toString(), u);
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
