package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player))
			return;
		Player p = (Player) e.getEntity();
		Player d = Util.getDamager(e);

		if (d == null)
			return;

		Guild pg = Manager.GUILD.getGuild(p);
		Guild dg = Manager.GUILD.getGuild(d);

		if (dg == null || pg == null)
			return;

		if (dg == pg) {
			if (dg.isPvp()) {
				e.setDamage(0);
			} else {
				e.setCancelled(true);
				Util.sendMsg(d, Lang.ERROR_CANT_ATTACK_PLAYER);
			}
		} //else if (AllianceManager.hasAlliance(pg, dg)) {
			//e.setDamage(0);
		//	Util.sendMsg(d, "&cNie mozesz atakowac tego gracza!");
		//}

	}

}
