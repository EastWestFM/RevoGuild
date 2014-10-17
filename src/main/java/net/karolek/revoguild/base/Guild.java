package net.karolek.revoguild.base;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Location;

import lombok.Data;
import net.karolek.revoguild.store.Entry;

@Data
public class Guild implements Entry {

	private final String	tag, name;
	private UUID			owner, leader;
	private final Cuboid	cuboid;
	private Location		location;
	private final long	createTime;
	private long			expireTime, lastExplodeTime, lastTakenLifeTime;
	private int				lives;
	private boolean		pvp, preDeleted;
	private final Set<UUID>	members	= new HashSet<UUID>(),
			invites = new HashSet<UUID>();

	@Override
	public void insert() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(boolean now) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

}
