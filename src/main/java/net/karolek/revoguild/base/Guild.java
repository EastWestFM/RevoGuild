package net.karolek.revoguild.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.AllianceManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.store.Entry;
import net.karolek.revoguild.utils.TimeUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

@Getter
@Setter
public class Guild implements Entry, Comparable<Guild> {

	private final String		tag;
	private final String		name;
	private User				owner;
	private User				leader;
	private Cuboid				cuboid;
	private final Treasure	treasure;
	private Location			home;
	private final long		createTime;
	private long				expireTime;
	private long				lastExplodeTime;
	private long				lastTakenLifeTime;
	private int					lives;
	private boolean			pvp;
	private boolean			preDeleted;
	private String				banAdmin;
	private long				banTime;
	private String				banReason;
	private final Set<User>	members			= new HashSet<User>();
	private final Set<User>	treasureUsers	= new HashSet<User>();
	private final Set<User>	invites			= new HashSet<User>();

	public Guild(String tag, String name, Player owner) {
		this.tag = tag;
		this.name = name;
		this.owner = UserManager.getUser(owner);
		this.leader = UserManager.getUser(owner);
		this.cuboid = new Cuboid(owner.getWorld().getName(), owner.getLocation().getBlockX(), owner.getLocation().getBlockZ(), Config.CUBOID_SIZE_START);
		this.treasure = new Treasure(this);
		this.home = owner.getLocation();
		this.createTime = System.currentTimeMillis();
		this.expireTime = System.currentTimeMillis() + TimeUtil.WEEK.getTime(Config.TIME_START);
		this.lastExplodeTime = System.currentTimeMillis() - TimeUtil.SECOND.getTime(Config.TNT_CANTBUILD_TIME);
		this.lastTakenLifeTime = System.currentTimeMillis();
		this.lives = Config.UPTAKE_LIVES_START;
		this.pvp = false;
		this.preDeleted = false;
		this.banAdmin = "";
		this.banTime = -1L;
		this.banReason = "";
	}

	public Guild(ResultSet rs) throws SQLException {
		this.tag = rs.getString("tag");
		this.name = rs.getString("name");
		this.owner = UserManager.getUser(rs.getString("owner"));
		this.leader = UserManager.getUser(rs.getString("leader"));
		this.cuboid = new Cuboid(rs.getString("cuboidWorld"), rs.getInt("cuboidX"), rs.getInt("cuboidZ"), rs.getInt("cuboidSize"));
		this.treasure = new Treasure(this, GuildPlugin.getStore().query("SELECT * FROM `{P}treasures` WHERE `tag` = '" + this.tag + "'"));
		this.home = new Location(Bukkit.getWorld(rs.getString("homeWorld")), rs.getInt("homeX"), rs.getInt("homeY"), rs.getInt("homeZ"));
		this.createTime = rs.getLong("createTime");
		this.expireTime = rs.getLong("expireTime");
		this.lastExplodeTime = System.currentTimeMillis() - TimeUtil.SECOND.getTime(Config.TNT_CANTBUILD_TIME);
		this.lastTakenLifeTime = rs.getLong("lastTakenLifeTime");
		this.lives = rs.getInt("lives");
		this.pvp = (rs.getInt("pvp") == 1);
		this.preDeleted = false;
		this.banAdmin = rs.getString("banAdmin");
		this.banReason = rs.getString("banReason");
		this.banTime = rs.getLong("banTime");

		ResultSet r = GuildPlugin.getStore().query("SELECT * FROM `{P}members` WHERE `tag` = '" + this.tag + "'");
		while (r.next())
			this.members.add(UserManager.getUser(r.getString("uuid")));

		ResultSet r1 = GuildPlugin.getStore().query("SELECT * FROM `{P}treasure_users` WHERE `tag` = '" + this.tag + "'");
		while (r1.next())
			this.treasureUsers.add(UserManager.getUser(r1.getString("uuid")));

	}

	public void openTreasure(Player p) {
		Treasure bp = this.treasure;
		Inventory inv = bp.getInv();
		inv.clear();
		inv.setContents(bp.getItems());
		p.openInventory(inv);
		p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1.0F, 1.0F);
	}

	public void closeTreasure(Player p, Inventory i) {
		Treasure bp = this.treasure;
		bp.setItems(i.getContents());
		bp.update(false);
		p.playSound(p.getLocation(), Sound.CHEST_CLOSE, 1.0F, 1.0F);
	}

	public Set<Player> getOnlineMembers() {
		Set<Player> online = new HashSet<Player>();
		for (User u : this.members) {
			OfflinePlayer op = u.getOfflinePlayer();
			if (op.isOnline())
				online.add(op.getPlayer());
		}
		return online;
	}

	public int getPoints() {

		String algorithm = Config.ALGORITHM_GUILD_POINTS;

		int points = 0;
		for (User u : this.members)
			points += u.getPoints();

		algorithm = algorithm.replace("{MEMBERS_POINTS}", Integer.toString(points));
		algorithm = algorithm.replace("{MEMBERS_NUM}", Integer.toString(this.members.size()));

		return Util.calculate(algorithm);

	}

	public int getKills() {
		int points = 0;
		for (User u : this.members)
			points += u.getKills();
		return points;
	}

	public int getDeaths() {
		int points = 0;
		for (User u : this.members)
			points += u.getDeaths();
		return points;
	}

	public void setOwner(User u) {
		this.owner = u;
		GuildPlugin.getStore().update(false, "UPDATE `{P}guilds` SET `owner` = '" + u.toString() + "' WHERE `tag` = '" + this.tag + "'");
	}

	public void setLeader(User u) {
		this.leader = u;
		GuildPlugin.getStore().update(false, "UPDATE `{P}guilds` SET `leader` = '" + u.toString() + "' WHERE `tag` = '" + this.tag + "'");
	}

	public boolean isOwner(User u) {
		return this.owner.equals(u);
	}

	public boolean isLeader(User u) {
		if (isOwner(u))
			return true;
		return this.leader.equals(u);
	}

	public boolean isMember(User u) {
		return this.members.contains(u);
	}

	public boolean isTreasureUser(User u) {
		return this.treasureUsers.contains(u);
	}

	public void addTreasureUser(User u) {
		GuildPlugin.getStore().update(false, "INSERT INTO `{P}treasure_users` (`id`,`uuid`,`tag`) VALUES(NULL, '" + u.toString() + "', '" + this.tag + "')");
		this.treasureUsers.add(u);
	}

	public void removeTreasureUser(User u) {
		GuildPlugin.getStore().update(false, "DELETE FROM `{P}treasure_users` WHERE `uuid` = '" + u.toString() + "' AND `tag` = '" + this.tag + "'");
		this.treasureUsers.remove(u);
	}

	public boolean hasInvite(User u) {
		return this.invites.contains(u);
	}

	public boolean addInvite(User u) {
		if (hasInvite(u))
			return false;
		return this.invites.add(u);
	}

	public boolean removeInvite(User u) {
		return this.invites.remove(u);
	}

	public boolean addMember(User u) {
		if (!hasInvite(u))
			return false;
		if (isMember(u))
			return false;
		removeInvite(u);
		this.members.add(u);
		GuildPlugin.getStore().update(false, "INSERT INTO `{P}members` (`id`,`uuid`,`tag`) VALUES(NULL, '" + u.toString() + "', '" + this.tag + "')");
		return true;
	}

	public boolean addSize() {
		if (!this.cuboid.addSize())
			return false;
		GuildPlugin.getStore().update(false, "UPDATE `{P}guilds` SET `cuboidSize` = '" + this.cuboid.getSize() + "' WHERE `tag` = '" + this.tag + "'");
		return true;
	}

	public boolean removeMember(User u) {
		if (!isMember(u))
			return false;
		this.members.remove(u);
		removeTreasureUser(u);
		GuildPlugin.getStore().update(false, "DELETE FROM `{P}members` WHERE `uuid` = '" + u.toString() + "' AND `tag` = '" + this.tag + "'");

		return true;
	}

	public boolean isBanned() {
		// if (this.banTime < 0)
		// return false;
		// //if (this.banTime == 0 || this.banTime > System.currentTimeMillis())
		// return true;
		// return false;
		return this.banTime > System.currentTimeMillis();
	}

	public boolean ban(String admin, String reason, long time) {
		if (isBanned())
			return false;
		this.banAdmin = admin;
		this.banReason = reason;
		this.banTime = time;
		GuildPlugin.getStore().update(false, "UPDATE `{P}guilds` SET `banAdmin` = '" + admin + "', `banTime`='" + time + "',`banReason`='" + reason + "' WHERE `tag` = '" + this.tag + "'");
		for (Player p : getOnlineMembers())
			p.kickPlayer(Lang.parse(Lang.BAN_KICKED, this));
		return true;
	}

	public boolean unban() {
		if (!isBanned())
			return false;
		this.banAdmin = "";
		this.banReason = "";
		this.banTime = -1L;
		GuildPlugin.getStore().update(false, "UPDATE `{P}guilds` SET `banAdmin` = '" + this.banAdmin + "', `banTime`='" + this.banTime + "',`banReason`='" + this.banReason + "' WHERE `tag` = '" + this.tag + "'");
		return true;
	}

	@Override
	public void insert() {
		String u = "INSERT INTO `{P}guilds` (`id`,`tag`,`name`,`owner`,`leader`,`cuboidWorld`,`cuboidX`,`cuboidZ`,`cuboidSize`,`homeWorld`,`homeX`,`homeY`,`homeZ`,`lives`,`createTime`,`expireTime`,`lastTakenLifeTime`,`pvp`,`banAdmin`,`banReason`,`banTime`) VALUES(NULL, '" + this.tag + "','" + this.name + "','" + this.owner + "','" + this.leader + "','" + this.cuboid.getWorld().getName() + "','" + this.cuboid.getCenterX() + "','" + this.cuboid.getCenterZ() + "','" + this.cuboid.getSize() + "','" + this.home.getWorld().getName() + "','" + this.home.getBlockX() + "','" + this.home.getBlockY() + "','" + this.home.getBlockZ() + "','" + this.lives + "','" + this.createTime + "','" + this.expireTime + "','" + this.lastTakenLifeTime + "','" + (this.pvp ? 1 : 0) + "', '" + this.banAdmin + "', '" + this.banReason + "', '" + this.banTime + "')";
		GuildPlugin.getStore().update(false, u);
	}

	@Override
	public void update(boolean now) {
		String update = "UPDATE `{P}guilds` SET `owner`='" + this.owner + "', `leader`='" + this.leader + "', `cuboidWorld`='" + this.cuboid.getWorld().getName() + "', `cuboidX`='" + this.cuboid.getCenterX() + "', `cuboidZ`='" + this.cuboid.getCenterZ() + "', `cuboidSize`='" + this.cuboid.getSize() + "', `homeWorld`='" + this.home.getWorld().getName() + "', `homeX`='" + this.home.getBlockX() + "', `homeY`='" + this.home.getBlockY() + "', `homeZ`='" + this.home.getBlockZ() + "', `createTime`='" + this.createTime + "', `expireTime`='" + this.expireTime + "', `lastTakenLifeTime` = '" + this.lastTakenLifeTime + "', `lives` = '" + this.lives + "', `pvp`='" + (this.pvp ? 1 : 0) + "',`banAdmin` = '" + this.banAdmin + "', `banTime`='" + this.banTime + "',`banReason`='" + this.banReason + "' WHERE `tag`='" + this.tag + "'";
		GuildPlugin.getStore().update(now, update);

	}

	@Override
	public void delete() {
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}guilds` WHERE `tag` = '" + this.tag + "'");
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}members` WHERE `tag` = '" + this.tag + "'");
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}treasure_users` WHERE `tag` = '" + this.tag + "'");
		this.treasure.delete();
		for (Alliance a : AllianceManager.getGuildAlliances(this))
			a.delete();
	}

	@Override
	public int compareTo(Guild o) {
		int result = 0;
		if (o.getPoints() == getPoints()) {
			result = 0;
		} else if (o.getPoints() < getPoints()) {
			result = 1;
		} else if (o.getPoints() > getPoints()) {
			result = -1;
		}
		return result;
	}

}
