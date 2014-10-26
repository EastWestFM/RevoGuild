package net.karolek.revoguild.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.store.Entry;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Getter
@Setter
public class User implements Entry, Comparable<User> {

	private final UUID	uuid;
	private final String	name;

	private int				kills;
	private int				deaths;
	private int				points;
	private int				timePlay;

	private long			joinTime;

	public User(Player p) {
		this.uuid = Config.USEUUID ? p.getUniqueId() : null;
		this.name = p.getName();
		this.kills = 0;
		this.deaths = 0;
		this.points = Config.RANKING_STARTPOINTS;
		this.timePlay = 0;
	}

	public User(ResultSet rs) throws SQLException {
		this.uuid = Config.USEUUID ? UUID.fromString(rs.getString("uuid")) : null;
		this.name = Config.USEUUID ? getOfflinePlayer().getName() : rs.getString("uuid");
		this.kills = rs.getInt("kills");
		this.deaths = rs.getInt("deaths");
		this.points = rs.getInt("points");
		this.timePlay = rs.getInt("timePlay");
	}

	@SuppressWarnings("deprecation")
	public OfflinePlayer getOfflinePlayer() {
		return Config.USEUUID ? Bukkit.getOfflinePlayer(this.uuid) : Bukkit.getOfflinePlayer(name);
	}

	public void addKill() {
		this.kills += 1;
		GuildPlugin.getStore().update(false, "UPDATE `{P}users` SET `kills` = '" + this.kills + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	public void addDeath() {
		this.deaths += 1;
		GuildPlugin.getStore().update(false, "UPDATE `{P}users` SET `deaths` = '" + this.deaths + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	public void addPoints(int points) {
		this.points += points;
		GuildPlugin.getStore().update(false, "UPDATE `{P}users` SET `points` = '" + this.points + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	public void removePoints(int points) {
		this.points -= points;
		GuildPlugin.getStore().update(false, "UPDATE `{P}users` SET `points` = '" + this.points + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	public void addTimePlay(int time) {
		this.timePlay += time;
		GuildPlugin.getStore().update(false, "UPDATE `{P}users` SET `timePlay` = '" + this.timePlay + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	@Override
	public int compareTo(User o) {
		int result = 0;
		if (o.getPoints() == this.points) {
			result = 0;
		} else if (o.getPoints() < this.points) {
			result = 1;
		} else if (o.getPoints() > this.points) {
			result = -1;
		}
		return result;
	}

	@Override
	public void insert() {
		GuildPlugin.getStore().update(true, "INSERT INTO `{P}users` (`id`,`uuid`,`points`,`kills`,`deaths`,`timePlay`) VALUES(NULL, '" + this.toString() + "','" + this.points + "','" + this.kills + "','" + this.deaths + "','" + this.timePlay + "')");
	}

	@Override
	public void update(boolean now) {
		GuildPlugin.getStore().update(now, "UPDATE `{P}users` SET `points` = '" + this.points + "',`kills` = '" + this.kills + "',`deaths` = '" + this.deaths + "',`timePlay` = '" + this.timePlay + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	@Override
	public void delete() {
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}users` WHERE `uuid` = '" + this.toString() + "'");
	}

	@Override
	public String toString() {
		return Config.USEUUID ? uuid.toString() : name;
	}

}
