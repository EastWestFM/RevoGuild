package net.karolek.revoguild.managers;

import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.utils.Util;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class TeleportManager {

	@Getter
	private static final HashMap<UUID, BukkitTask>	teleports	= new HashMap<>();

	public static void teleport(Player p, Location loc, int time) {
		if (p.hasPermission("rg.teleport.nodelay"))
			p.teleport(loc);
		else
			teleportWithDelay(p, loc, time);
	}

	private static void teleportWithDelay(final Player p, final Location loc, int time) {
		Util.sendMsg(p, Lang.TELEPORT_START.replace("{TIME}", Integer.toString(time)));
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * time + 100, 5));
		if (teleports.get(p.getUniqueId()) != null)
			teleports.remove(p.getUniqueId()).cancel();
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				if (p.isOnline()) {
					p.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
					p.removePotionEffect(PotionEffectType.CONFUSION);
					Util.sendMsg(p, Lang.TELEPORT_END);
					teleports.remove(p.getUniqueId());
				}
			}
		}.runTaskLater(GuildPlugin.getPlugin(), time * 20);
		teleports.put(p.getUniqueId(), task);
	}
}
