package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.AllianceManager;
import net.karolek.revoguild.managers.CombatManager;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.ParticleUtil;
import net.karolek.revoguild.utils.ParticleUtil.ParticleType;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    @EventHandler
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

        Guild pg = GuildManager.getGuild(p);
        Guild dg = GuildManager.getGuild(d);

        boolean fight = true;

        if (dg == null || pg == null) {
            fight = false;
            return;
        }

        if (dg == pg) {
            if (dg.isPvp()) {
                e.setDamage(0);
            } else {
                e.setCancelled(true);
                ParticleUtil.sendPartileToPlayer(d, ParticleType.HEART, p.getEyeLocation(), 0.25F, 0.25F, 0.25F, 8, 3);
                Util.sendMsg(d, Lang.ERROR_CANT_ATTACK_PLAYER);
                fight = false;
                return;
            }
        } else if (AllianceManager.hasAlliance(pg, dg)) {
            e.setDamage(0);
            Util.sendMsg(d, Lang.ERROR_CANT_ATTACK_PLAYER);
        }

        if (Config.ESCAPE_ENABLED && fight) {
            if (!CombatManager.isInFight(p))
                Util.sendMsg(p, Lang.INFO_FIGHT_START);
            if (!CombatManager.isInFight(d))
                Util.sendMsg(d, Lang.INFO_FIGHT_START);

            CombatManager.setLastFight(p);
            CombatManager.setLastFight(d);
        }

    }


}
