package net.karolek.revoguild.nametags.modes;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.data.Lang;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.nametags.NameTag;
import net.karolek.revoguild.nametags.NameTagMode;
import net.karolek.revoguild.utils.Util;

public class ScoreBoardNameTag implements NameTag {

	@Override
	public void initPlayer(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();

		Guild g = Manager.GUILD.getGuild(p);
		Team t;
		for (Guild o : Manager.GUILD.getGuilds()) {
			t = sb.getTeam(o.getTag());
			if (t == null) {
				t = sb.registerNewTeam(o.getTag());
			}
			if (g == null) {
				t.setPrefix(parse(Config.TAG_COLOR_ENEMY, o));
			} else if (g.getTag() == o.getTag()) {
				if (g.isPvp()) {
					t.setPrefix(parse(Config.TAG_COLOR_FRIENDPVP, o));
				} else {
					t.setPrefix(parse(Config.TAG_COLOR_FRIEND, o));
				}
			} else if (Manager.ALLIANCE.hasAlliance(o, g)) {
				t.setPrefix(parse(Config.TAG_COLOR_ALLIANCE, o));
			} else {
				t.setPrefix(parse(Config.TAG_COLOR_ENEMY, o));
			}
		}
		Team noguild = sb.getTeam("noguild");
		if (noguild == null) {
			noguild = sb.registerNewTeam("noguild");
			noguild.setAllowFriendlyFire(true);
			noguild.setCanSeeFriendlyInvisibles(false);
			noguild.setPrefix(parse(Config.TAG_COLOR_NOGUILD, null));
		}
		p.setScoreboard(sb);

		for (Player online : Bukkit.getOnlinePlayers()) {
			online.getScoreboard().getTeam(g != null ? g.getTag() : "noguild").addPlayer(p);

			Guild onlineguild = Manager.GUILD.getGuild(online);

			p.getScoreboard().getTeam(onlineguild != null ? onlineguild.getTag() : "noguild").addPlayer(online);
		}
	}

	@Override
	public void createGuild(Guild g, Player p) {
		for (Player o : Bukkit.getOnlinePlayers()) {
			Scoreboard sb = o.getScoreboard();

			Team t = sb.registerNewTeam(g.getTag());
			if (o == p) {
				t.setPrefix(parse(Config.TAG_COLOR_FRIEND, g));
			} else {
				t.setPrefix(parse(Config.TAG_COLOR_ENEMY, g));
			}
			t.addPlayer(p);
		}
	}

	@Override
	public void removeGuild(Guild g) {
		for (Player p : Bukkit.getOnlinePlayers()) {

			Scoreboard sb = p.getScoreboard();

			sb.getTeam(g.getTag()).unregister();

			Team noguild = sb.getTeam("noguild");

			for (Player guildplayer : g.getOnlineMembers())
				noguild.addPlayer(guildplayer);
		}
	}

	@Override
	public void setPvp(Guild g) {
		for (Player p : g.getOnlineMembers()) {
			Team t = p.getScoreboard().getTeam(g.getTag());
			if (g.isPvp()) {
				t.setPrefix(parse(Config.TAG_COLOR_FRIENDPVP, g));
			} else {
				t.setPrefix(parse(Config.TAG_COLOR_FRIEND, g));
			}
		}
	}

	@Override
	public void joinToGuild(Guild g, Player p) {
		for (Player o : Bukkit.getOnlinePlayers())
			o.getScoreboard().getTeam(g.getTag()).addPlayer(p);

		if (g.isPvp()) {
			p.getScoreboard().getTeam(g.getTag()).setPrefix(parse(Config.TAG_COLOR_FRIENDPVP, g));
		} else {
			p.getScoreboard().getTeam(g.getTag()).setPrefix(parse(Config.TAG_COLOR_FRIEND, g));
		}
	}

	@Override
	public void leaveFromGuild(Guild g, OfflinePlayer p) {
		for (Player o : Bukkit.getOnlinePlayers())
			o.getScoreboard().getTeam("noguild").addPlayer(p);

		if (p.isOnline())
			p.getPlayer().getScoreboard().getTeam(g.getTag()).setPrefix(parse(Config.TAG_COLOR_ENEMY, g));
	}

	@Override
	public void createAlliance(Guild g, Guild o) {
		for (Player p : g.getOnlineMembers()) {
			Team t = p.getScoreboard().getTeam(o.getTag());
			if (t != null)
				t.setPrefix(parse(Config.TAG_COLOR_ALLIANCE, o));
		}

		for (Player p : o.getOnlineMembers()) {
			Team t = p.getScoreboard().getTeam(g.getTag());
			if (t != null)
				t.setPrefix(parse(Config.TAG_COLOR_ALLIANCE, g));
		}
	}

	@Override
	public void removeAlliance(Guild g, Guild o) {
		for (Player p : g.getOnlineMembers()) {
			Team t = p.getScoreboard().getTeam(o.getTag());
			if (t != null)
				t.setPrefix(parse(Config.TAG_COLOR_ENEMY, o));
		}

		for (Player p : o.getOnlineMembers()) {
			Team t = p.getScoreboard().getTeam(g.getTag());
			if (t != null)
				t.setPrefix(parse(Config.TAG_COLOR_ENEMY, g));
		}
	}

	@Override
	public NameTagMode getNameTagMode() {
		return NameTagMode.SCOREBOARD;
	}

	private static String parse(String color, Guild g) {
		if (g == null)
			return Util.fixColor(color);
		String format = Config.TAG_FORMAT;
		format = format.replace("{COLOR}", color);
		format = Lang.parse(format, g);
		return format;
	}

}
