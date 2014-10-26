package net.karolek.revoguild.listeners;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.Logger;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		final Player p = e.getEntity();
		Player k = p.getKiller();
		
		User pUser = Manager.USER.getUser(p);
		User kUser = null;
		
		if(k == null) {
			pUser.addDeath();
			return;
		}
		
		kUser = Manager.USER.getUser(k);
				
		String algorithmWin = Config.RANKING_ALGORITHM_WIN;
		algorithmWin = algorithmWin.replace("{KILLER_POINTS}", Integer.toString(kUser.getPoints()));
		algorithmWin = algorithmWin.replace("{PLAYER_POINTS}", Integer.toString(pUser.getPoints()));

		ScriptEngineManager manager= new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("js");
		
		int winPoints = 1;
		try {
			winPoints = ((Double) engine.eval(algorithmWin)).intValue();
		} catch (ScriptException ex) {
			Logger.exception(ex);
		}
		
		String algorithmLose = Config.RANKING_ALGORITHM_LOSE;
		algorithmLose = algorithmLose.replace("{WIN_POINTS}", Integer.toString(winPoints));
		
		int losePoints = 0;
		
		try {
			losePoints = ((Double) engine.eval(algorithmLose)).intValue();
		} catch (ScriptException ex) {
			Logger.exception(ex);
		}
		
		
		
		
		pUser.addDeath();
		kUser.addKill();
		pUser.removePoints(losePoints);
		kUser.addPoints(winPoints);
		
		Guild pGuild = Manager.GUILD.getGuild(p);
		Guild kGuild = Manager.GUILD.getGuild(k);
		
		String pGuildTag = pGuild != null ? Lang.parse(Config.CHAT_FORMAT_TAGDEATHMSG, pGuild) : "";
		String kGuildTag = kGuild != null ? Lang.parse(Config.CHAT_FORMAT_TAGDEATHMSG, kGuild) : "";
		
		String mes = Config.RANKING_DEATHMESSAGE;
		mes = mes.replace("{PGUILD}", pGuildTag);
		mes = mes.replace("{PLAYER}", p.getName());
		mes = mes.replace("{LOSEPOINTS}", "-" + Integer.toString(losePoints));
		mes = mes.replace("{KGUILD}", kGuildTag);
		mes = mes.replace("{KILLER}", k.getName());
		mes = mes.replace("{WINPOINTS}", winPoints > 0 ? "+" + Integer.toString(winPoints) : "-" + Integer.toString(winPoints));
		
		if(mes != "")
			e.setDeathMessage(mes);

	}

}
