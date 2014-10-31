package net.karolek.revoguild.managers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Alliance;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.utils.Logger;

public class AllianceManager {

	@Getter
	private static final List<Alliance>	alliances	= new ArrayList<>();
	@Getter
	private static final List<String>	invites		= new ArrayList<>();

	public static boolean hasAlliance(Guild g, Guild o) {
		for (Alliance a : alliances)
			if ((a.getGuild1().equals(g) && a.getGuild2().equals(o)) || (a.getGuild2().equals(g) && a.getGuild1().equals(o)))
				return true;
		return false;
	}

	public static boolean createAlliance(Guild g, Guild o) {
		if (hasAlliance(g, o))
			return false;

		Alliance a = new Alliance(g, o);
		a.insert();

		NameTagManager.createAlliance(g, o);

		return alliances.add(a);
	}

	public static boolean removeAlliance(Guild g, Guild o) {
		if (!hasAlliance(g, o))
			return false;

		Alliance a = getAlliance(g, o);
		a.delete();

		NameTagManager.removeAlliance(g, o);

		return alliances.remove(a);
	}

	public static Alliance getAlliance(Guild g, Guild o) {
		for (Alliance a : alliances)
			if ((a.getGuild1().equals(g) && a.getGuild2().equals(o)) || (a.getGuild2().equals(g) && a.getGuild1().equals(o)))
				return a;
		return null;
	}

	public static Set<Alliance> getGuildAlliances(Guild g) {
		Set<Alliance> all = new HashSet<Alliance>();
		for (Alliance a : alliances)
			if (a.getGuild1().equals(g) || a.getGuild2().equals(g))
				all.add(a);
		return all;
	}

	public static boolean removeAlliances(Guild g) {
		for (Alliance a : getGuildAlliances(g)) {
			a.delete();
			alliances.remove(a);
		}
		return true;
	}

	public static void enable() {
		ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}alliances`");
		try {
			while (rs.next()) {
				Alliance a = new Alliance(rs);
				alliances.add(a);
			}
			Logger.info("Loaded " + alliances.size() + " alliances!");
		} catch (SQLException e) {
			Logger.warning("An error occurred while loading alliances!", "Error: " + e.getMessage());
			Logger.exception(e);
		}
	}

}
