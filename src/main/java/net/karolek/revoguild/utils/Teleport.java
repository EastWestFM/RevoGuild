package net.karolek.revoguild.utils;

import java.util.HashMap;
import java.util.UUID;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class Teleport {

	@Getter
	private static final HashMap<UUID, BukkitTask>	teleports	= new HashMap<UUID, BukkitTask>();

	public static void teleportWithDelay(final Player p, final Location loc, int time, final String msg) {
		Util.sendMsg(p, "&cTeleport nastapi za &7" + Util.secondsToString(time) + "&c! Prosze sie nie ruszac!"); //TODO wiadmosc z pliku
		p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 20 * time+100, 5));
		if (teleports.get(p.getUniqueId()) != null)
			teleports.remove(p.getUniqueId()).cancel();
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				if (p.isOnline()) {
					p.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
					p.removePotionEffect(PotionEffectType.CONFUSION);
					if (msg != null)
						Util.sendMsg(p, msg);
					teleports.remove(p.getUniqueId());
				}
			}
		}.runTaskLater(GuildPlugin.getPlugin(), time * 20);
		teleports.put(p.getUniqueId(), task);
	}

}
