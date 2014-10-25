package net.karolek.revoguild.base;

import java.sql.ResultSet;
import java.sql.SQLException;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.store.Entry;

@Getter
@Setter
public class Alliance implements Entry {

	private final Guild	guild1, guild2;

	public Alliance(Guild guild1, Guild guild2) {
		this.guild1 = guild1;
		this.guild2 = guild2;
	}

	public Alliance(ResultSet rs) throws SQLException {
		this.guild1 = Manager.GUILD.getGuild(rs.getString("guild_1"));
		this.guild2 = Manager.GUILD.getGuild(rs.getString("guild_2"));
	}

	@Override
	public void insert() {
		GuildPlugin.getStore().update(true, "INSERT INTO `{P}alliances` (`id`,`guild_1`,`guild_2`) VALUES(NULL, '" + guild1.getTag() + "', '" + guild2.getTag() + "')");
	}

	@Override
	public void update(boolean paramBoolean) {
		throw new RuntimeException("Can not update this object!");
	}

	@Override
	public void delete() {
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}alliances` WHERE `guild_1` = '" + guild1.getTag() + "' AND `guild_2` ='" + guild2.getTag() + "'");
		GuildPlugin.getStore().update(true, "DELETE FROM `{P}alliances` WHERE `guild_1` = '" + guild2.getTag() + "' AND `guild_2` ='" + guild1.getTag() + "'");
	}

}
