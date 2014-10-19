package net.karolek.revoguild.listeners;

import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffectType;

public class TeleportListener implements Listener {

	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		Location from = e.getFrom();
		Location to = e.getTo();

		if ((!from.getWorld().equals(to.getWorld())) || from.getX() != to.getX() || from.getY() != to.getY() || from.getZ() != to.getZ()) {
			Player p = e.getPlayer();
			if (!Manager.TELEPORT.getTeleports().containsKey(p.getUniqueId()))
				return;
			Manager.TELEPORT.getTeleports().remove(p.getUniqueId()).cancel();
			p.removePotionEffect(PotionEffectType.CONFUSION);
			Util.sendMsg(p, Lang.TELEPORT_ERROR);
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		if (!Manager.TELEPORT.getTeleports().containsKey(p.getUniqueId()))
			return;
		Manager.TELEPORT.getTeleports().remove(p.getUniqueId()).cancel();
		p.removePotionEffect(PotionEffectType.CONFUSION);
		Util.sendMsg(p, Lang.TELEPORT_ERROR);
	}

}
