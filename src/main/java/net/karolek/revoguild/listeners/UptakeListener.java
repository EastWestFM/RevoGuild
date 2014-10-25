package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.SpaceUtil;
import net.karolek.revoguild.utils.TimeUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class UptakeListener implements Listener {

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		Block b = e.getBlockPlaced();

		if (b == null)
			return;

		Guild g = Manager.GUILD.getGuild(b.getLocation());

		if (g == null)
			return;

		for (Location l : SpaceUtil.getSquare(b.getLocation(), 2, 2)) {
			if (l.getBlock().getType().equals(Material.DRAGON_EGG)) {
				e.setCancelled(true);
				Util.sendMsg(e.getPlayer(), Lang.ERROR_CANT_BUILD_NEAR_EGG);
				return;
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		Block b = e.getClickedBlock();

		if (b == null)
			return;

		if (!b.getType().equals(Material.DRAGON_EGG))
			return;

		Guild g = Manager.GUILD.getGuild(b.getLocation());

		if (g == null)
			return;

		e.setCancelled(true);

		Player p = e.getPlayer();

		if (g.isMember(Manager.USER.getUser(p)))
			return;

		Guild o = Manager.GUILD.getGuild(p);

		if (o == null)
			return;

		if (Manager.ALLIANCE.hasAlliance(g, o))
			return;

		if ((g.getLastTakenLifeTime() + TimeUtil.HOUR.getTime(Config.UPTAKE_LIVES_TIME)) > System.currentTimeMillis()) {
			Util.sendMsg(p, Lang.ERROR_CANT_TAKE_LIFE);
			return;
		}

		if (g.getLives() <= 1) {
			Manager.GUILD.removeGuild(g);
			Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_TAKEN, g, o, p));
			p.playSound(g.getCuboid().getCenter(), Sound.ENDERDRAGON_DEATH, 20, 20);
			for (int i = 0; i < 10; i++)
				g.getCuboid().getWorld().strikeLightning(g.getCuboid().getCenter());
		} else {
			g.setLives(g.getLives() - 1);
			g.setLastTakenLifeTime(System.currentTimeMillis());
			g.update(false);
			Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_LIFE_TAKEN, g, o, p));
		}
	}

	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent e) {

		for (Block b : e.getBlocks()) {
			BlockFace dir = e.getDirection();
			Location l = b.getLocation();
			if (b.getType().equals(Material.DRAGON_EGG) || l.add(dir.getModX(), dir.getModY(), dir.getModZ()).getBlock().getType().equals(Material.DRAGON_EGG)) {
				if (Manager.GUILD.getGuild(b.getLocation()) != null) {
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent e) {
		Block b = e.getBlock();
		if (b.getType().equals(Material.DRAGON_EGG))
			if (Manager.GUILD.getGuild(b.getLocation()) != null) {
				e.setCancelled(true);
				return;
			}
	}

	@EventHandler
	public void onExplode(EntityExplodeEvent e) {
		if (Manager.GUILD.getGuild(e.getEntity().getLocation()) == null)
			return;

		for (Block b : e.blockList()) {
			if (b.getType().equals(Material.DRAGON_EGG)) {
				e.setCancelled(true);
				return;
			};
		}
	}

}
