package net.karolek.revoguild.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.store.Entry;
import net.karolek.revoguild.store.values.IntegerValue;
import net.karolek.revoguild.store.values.Valueable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

@Getter
@Setter
public class User implements Entry {

	private final UUID	uuid;
	private final String	name;

	private IntegerValue				kills;
	private IntegerValue				deaths;
	private IntegerValue				points;
	private IntegerValue				timePlay;

	private long			joinTime;

	public User(Player p) {
		this.uuid = Config.USEUUID ? p.getUniqueId() : null;
		this.name = p.getName();
		this.kills = new IntegerValue("kills", this, 0);
		this.deaths = new IntegerValue("deaths", this, 0);
		this.points = new IntegerValue("points", this, Config.RANKING_STARTPOINTS);
		this.timePlay = new IntegerValue("kills", this, 0);
	}

	public User(ResultSet rs) throws SQLException {
		this.uuid = Config.USEUUID ? UUID.fromString(rs.getString("uuid")) : null;
		this.name = rs.getString("lastNick");
		this.kills = new IntegerValue("kills", this, rs.getInt("kills"));
		this.deaths = new IntegerValue("deaths", this, rs.getInt("deaths"));
		this.points = new IntegerValue("points", this, rs.getInt("points"));
		this.timePlay = new IntegerValue("timePlay", this, rs.getInt("timePlay"));
	}

	@SuppressWarnings("deprecation")
	public OfflinePlayer getOfflinePlayer() {
		return Config.USEUUID ? Bukkit.getOfflinePlayer(this.uuid) : Bukkit.getOfflinePlayer(name);
	}

	/*public void addKill() {
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
	}*/

	@Override
	public void insert() {
		GuildPlugin.getStore().update(true, "INSERT INTO `{P}users` (`id`,`uuid`,`lastNick`,`points`,`kills`,`deaths`,`timePlay`) VALUES(NULL, '" + this.toString() + "','" + this.name + "','" + this.points.get() + "','" + this.kills.get() + "','" + this.deaths.get() + "','" + this.timePlay.get() + "')");
	}

	@Override
	public void update(boolean now) {
		GuildPlugin.getStore().update(now, "UPDATE `{P}users` SET `lastNick` = '" + this.name + "',`points` = '" + this.points.get() + "',`kills` = '" + this.kills.get() + "',`deaths` = '" + this.deaths.get() + "',`timePlay` = '" + this.timePlay.get() + "' WHERE `uuid` = '" + this.toString() + "'");
	}

	@Override
	public void delete() {
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}users` WHERE `uuid` = '" + this.toString() + "'");
	}

	@Override
	public String toString() {
		return Config.USEUUID ? uuid.toString() : name;
	}

	@Override
	public void update(Valueable value) {
		GuildPlugin.getStore().update(false, "UPDATE `{P}users` SET `" + value.getFieldName() + "` = '" + value.getStringValue() + "' WHERE `uuid` = '" + this.toString() + "'");
	}

}
