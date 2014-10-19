package net.karolek.revoguild.nametags;

import net.karolek.revoguild.base.Guild;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface NameTag {

	public void initPlayer(Player p);
	
	public void createGuild(Guild g, Player p);
	
	public void removeGuild(Guild g);

	public void setPvp(Guild g);
	
	public void joinToGuild(Guild g, Player p);
	
	public void leaveFromGuild(Guild g, OfflinePlayer p);
	
	public void createAlliance(Guild g, Guild o);
	
	public void removeAlliance(Guild g, Guild o);
	
	public NameTagMode getNameTagMode();
	
}
