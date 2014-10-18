package net.karolek.revoguild.base;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import lombok.Data;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.store.Entry;
import net.karolek.revoguild.utils.TimeUtil;

@Data
public class Guild implements Entry {

	private final String	tag, name;
	private UUID			owner, leader;
	private final Cuboid	cuboid;
	private Location		home;
	private final long	createTime;
	private long			expireTime, lastExplodeTime, lastTakenLifeTime;
	private int				lives;
	private boolean		pvp, preDeleted = false;
	private final Set<UUID>	members	= new HashSet<UUID>(),
			invites = new HashSet<UUID>();
	
	public Guild(String tag, String name, Player owner) {
		this.tag = tag;
		this.name = name;
		this.owner = owner.getUniqueId();
		this.leader = owner.getUniqueId();
		this.cuboid = new Cuboid(owner.getWorld().getName(), owner.getLocation().getBlockX(), owner.getLocation().getBlockZ(), 24);
		this.home = owner.getLocation();
		this.createTime = System.currentTimeMillis();
		this.expireTime = System.currentTimeMillis() + TimeUtil.WEEK.getTime(2);
		this.lives = Config.LIVES_AMOUNT;
		this.pvp = false;
	}

	public Guild(ResultSet rs) throws SQLException {
		this.tag = rs.getString("tag");
		this.name = rs.getString("name");
		this.owner = UUID.fromString(rs.getString("owner"));
		this.leader = UUID.fromString(rs.getString("leader"));
		this.cuboid = new Cuboid(rs.getString("cuboidWorld"), rs.getInt("cuboidX"), rs.getInt("cuboidZ"), rs.getInt("cuboidSize"));
		this.home = new Location(Bukkit.getWorld(rs.getString("homeWorld")), rs.getInt("homeX"), rs.getInt("homeY"), rs.getInt("homeZ"));
		this.createTime = rs.getLong("createTime");
		this.expireTime = rs.getLong("expireTime");
		this.pvp = (rs.getInt("pvp") == 1);
		ResultSet r = GuildPlugin.getStore().query("SELECT * FROM `{P}members` WHERE `tag` = '" + this.tag + "'");
		while (r.next())
			this.members.add(UUID.fromString(r.getString("uuid")));
	}
	
	public Set<Player> getOnlineMembers() {
		Set<Player> online = new HashSet<Player>();
		for (UUID u : this.members) {
			Player p = Bukkit.getPlayer(u);
			if (p != null)
				online.add(p);
		}
		return online;
	}

	public boolean isOwner(UUID u) {
		return this.owner.equals(u);
	}

	public boolean isLeader(UUID u) {
		if (isOwner(u))
			return true;
		return this.leader.equals(u);
	}

	public boolean isMember(UUID u) {
		return this.members.contains(u);
	}

	public boolean hasInvite(UUID u) {
		return this.invites.contains(u);
	}

	public boolean addInvite(UUID u) {
		if (hasInvite(u))
			return false;
		return this.invites.add(u);
	}

	public boolean removeInvite(UUID u) {
		return this.invites.remove(u);
	}

	public boolean addMember(UUID u) {
		if (!hasInvite(u))
			return false;
		if (isMember(u))
			return false;
		removeInvite(u);
		this.members.add(u);
		GuildPlugin.getStore().update("INSERT INTO `{P}members` SET `uuid` = '" + u + "', `tag` = '" + this.tag + "'");
		return true;
	}

	public boolean removeMember(UUID u) {
		if (!isMember(u))
			return false;
		this.members.remove(u);
		GuildPlugin.getStore().update("DELETE FROM `{P}members` WHERE `uuid` = '" + u + "' AND `tag` = '" + this.tag + "'");
		return true;
	}

	@Override
	public void insert() {
		GuildPlugin.getStore().update("INSERT INTO `{P}guilds` SET `tag` = '" + this.tag + "',  `name` = '" + this.name + "',  `owner` = '" + this.owner + "',  `leader` = '" + this.leader + "',  `cuboidWorld` = '" + this.cuboid.getWorld().getName() + "',  `cuboidX` = '" + this.cuboid.getCenterX() + "',  `cuboidZ` = '" + this.cuboid.getCenterZ() + "',  `cuboidSize` = '" + this.cuboid.getSize() + "',  `homeWorld` = '" + this.home.getWorld().getName() + "',  `homeX` = '" + this.home.getBlockX() + "',  `homeY` = '" + this.home.getBlockY() + "',  `homeZ` = '" + this.home.getBlockZ() + "',  `lives` = '" + this.lives + "', `createTime` = '" + this.createTime + "',  `expireTime` = '" + this.expireTime + "',  `pvp` = '" + (this.pvp ? 1 : 0) + "'");
	}

	@Override
	public void update(boolean now) {
		String update = "UPDATE `{P}guilds` SET `owner`='" + this.owner + "', `leader`='" + this.leader + "', `cuboidWorld`='" + this.cuboid.getWorld().getName() + "', `cuboidX`='" + this.cuboid.getCenterX() + "', `cuboidZ`='" + this.cuboid.getCenterZ() + "', `cuboidSize`='" + this.cuboid.getSize() + "', `homeWorld`='" + this.home.getWorld().getName() + "', `homeX`='" + this.home.getBlockX() + "', `homeY`='" + this.home.getBlockY() + "', `homeZ`='" + this.home.getBlockZ() + "', `createTime`='" + this.createTime + "', `expireTime`='" + this.expireTime + "', `lives` = '" + this.lives + "', `pvp`='" + (this.pvp ? 1 : 0) + "' WHERE `tag`='" + this.tag + "'";
		if (now)
			GuildPlugin.getStore().updateNow(update);
		else
			GuildPlugin.getStore().update(update);
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
