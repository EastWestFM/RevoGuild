package net.karolek.revoguild.listeners;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.managers.CombatManager;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.managers.UserManager;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Util;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		Player k = p.getKiller();
		
		CombatManager.createPlayer(p);
		
		User pUser = UserManager.getUser(p);
		User kUser = null;
		
		if(k == null) {
			pUser.addDeath();
			TabThread.restart();
			return;
		}
		
		kUser = UserManager.getUser(k);
				
		String algorithmWin = Config.ALGORITHM_RANKING_WIN;
		algorithmWin = algorithmWin.replace("{KILLER_POINTS}", Integer.toString(kUser.getPoints()));
		algorithmWin = algorithmWin.replace("{PLAYER_POINTS}", Integer.toString(pUser.getPoints()));

		
		int winPoints = Util.calculate(algorithmWin);
	
		String algorithmLose = Config.ALGORITHM_RANKING_LOSE;
		algorithmLose = algorithmLose.replace("{WIN_POINTS}", Integer.toString(winPoints));
		
		int losePoints = Util.calculate(algorithmLose);
		
		pUser.addDeath();
		kUser.addKill();
		pUser.removePoints(losePoints);
		kUser.addPoints(winPoints);
		
		Guild pGuild = GuildManager.getGuild(p);
		Guild kGuild = GuildManager.getGuild(k);
		
		String pGuildTag = pGuild != null ? Lang.parse(Config.CHAT_FORMAT_TAGDEATHMSG, pGuild) : "";
		String kGuildTag = kGuild != null ? Lang.parse(Config.CHAT_FORMAT_TAGDEATHMSG, kGuild) : "";
		
		String mes = Config.RANKING_DEATHMESSAGE;
		mes = mes.replace("{PGUILD}", pGuildTag);
		mes = mes.replace("{PLAYER}", p.getName());
		mes = mes.replace("{LOSEPOINTS}", "-" + Integer.toString(losePoints));
		mes = mes.replace("{KGUILD}", kGuildTag);
		mes = mes.replace("{KILLER}", k.getName());
		mes = mes.replace("{WINPOINTS}", winPoints > 0 ? "+" + Integer.toString(winPoints) : "-" + Integer.toString(winPoints));
		
		TabThread.restart();
		
		if(mes != "")
			e.setDeathMessage(Util.fixColor(mes));
	
		
	}

}
