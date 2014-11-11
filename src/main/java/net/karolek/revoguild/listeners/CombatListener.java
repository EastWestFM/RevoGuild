package net.karolek.revoguild.listeners;

import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.CombatManager;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.isCancelled())
            return;
        if (!(e.getDamage() > 0))
            return;
        if (!(e.getEntity() instanceof Player))
            return;
        if (Util.getDamager(e) == null)
            return;

        Player p = (Player) e.getEntity();
        Player d = Util.getDamager(e);

        if(p == d) return;

        if (Config.ESCAPE_ENABLED) {
            if (!CombatManager.isInFight(p))
                Util.sendMsg(p, Lang.INFO_FIGHT_START);
            if (!CombatManager.isInFight(d))
                Util.sendMsg(d, Lang.INFO_FIGHT_START);

            CombatManager.setLastFight(p);
            CombatManager.setLastFight(d);
        }

    }

}
