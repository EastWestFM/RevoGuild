package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.TimeUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CrystalListener implements Listener {

	@EventHandler
	public void onPistonEvent(BlockPistonExtendEvent e) {
		Guild g = Manager.GUILD.getGuild(e.getBlock().getLocation());

		if (g == null)
			return;

		if (g.getCrystal(e.getBlock().getLocation()) != null) {
			e.setCancelled(true);
			return;
		}

		for (Block b : e.getBlocks()) {
			BlockFace face = e.getDirection();
			if (g.getCrystal(b.getLocation().add(face.getModX(), face.getModY(), face.getModZ())) != null) {
				e.setCancelled(true);
				return;
			}
		}
	}
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		Entity en = e.getEntity();
		
		Guild g = Manager.GUILD.getGuild(en.getLocation());
		
		if(g == null) return;
		
		if(!(en instanceof EnderCrystal)) return;
		
		Player d = Util.getDamager(e);
		
		Guild dG = Manager.GUILD.getGuild(d);
		
		if(dG == null) {
			e.setCancelled(true);
			return;
		}
		
		if(g.isMember(d.getUniqueId())) {
			e.setCancelled(true);
			return;
		}
		
		if((g.getLastTakenLifeTime() + TimeUtil.HOUR.getTime(Config.LIVES_TIME)) > System.currentTimeMillis()) {
			Util.sendMsg(d, Lang.ERROR_CANT_TAKE_LIFE);
			e.setCancelled(true);
			return;
		}
		
		if(g.getLives() <= 1) {
			Manager.GUILD.removeGuild(g);
			Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_TAKEN, g, dG, d));
			e.setCancelled(false);
		} else {
			g.setLives(g.getLives() - 1);
			g.setLastTakenLifeTime(System.currentTimeMillis());
			g.update(false);
			e.setCancelled(true);
			Util.sendMsg(Util.getOnlinePlayers(), Lang.parse(Lang.BC_GUILD_LIFE_TAKEN, g, dG, d));
			return;
		}
	}

}
