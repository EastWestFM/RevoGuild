package net.karolek.revoguild.manager.types;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.manager.IManager;
import net.karolek.revoguild.nametags.NameTag;
import net.karolek.revoguild.nametags.NameTagMode;
import net.karolek.revoguild.nametags.modes.ScoreBoardNameTag;
import net.karolek.revoguild.nametags.modes.TagApiNameTag;
import net.karolek.revoguild.utils.Logger;

public class TagManager implements IManager {

	@Getter
	private NameTag	nameTag;

	@Override
	public void enable() throws Exception {
		new BukkitRunnable() {

			@Override
			public void run() {
				switch (NameTagMode.getByName(Config.TAG_MODE)) {
					case TAG_API:
						Plugin p = Bukkit.getPluginManager().getPlugin("TagAPI");
						if(p == null || !p.isEnabled()) {
							Logger.warning("Can not find valid TagAPI instance!", "Using ScoreBoard nametags!");
							nameTag = new ScoreBoardNameTag();
							break;
						}
						nameTag = new TagApiNameTag();
						break;
					case SCOREBOARD:
					default:
						nameTag = new ScoreBoardNameTag();
						break;
				}				
				Logger.info("Using '" + nameTag.getClass().getSimpleName().replace("NameTag", "") + "' to support nametags!");
			}
			
		}.runTask(GuildPlugin.getPlugin());
		
	}

	@Override
	public void disable() {
		nameTag = null;
	}

}
