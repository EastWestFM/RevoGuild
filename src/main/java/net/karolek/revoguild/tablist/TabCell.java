package net.karolek.revoguild.tablist;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.utils.PacketUtil;
import net.karolek.revoguild.utils.Util;

@Getter
@Setter
public class TabCell {

	private final Tab		tab;
	private String			prefix;
	private final String	name;
	private String			suffix;
	private int				ping;

	protected boolean		sent;
	protected boolean		teamExists;
	protected boolean		toRemove;

	public TabCell(Tab tab, String prefix, String name, String suffix, int ping) {
		this.tab = tab;
		this.prefix = Util.fixColor(prefix.substring(0, Math.min(prefix.length(), 16)));
		this.name = Util.fixColor(name.substring(0, Math.min(name.length(), 16)));
		this.suffix = Util.fixColor(suffix.substring(0, Math.min(suffix.length(), 16)));
		this.ping = ping;
		this.teamExists = true;
		this.sent = false;
	}

	public TabCell(Tab tab, String name) {
		this.tab = tab;
		this.name = Util.fixColor(name.substring(0, Math.min(name.length(), 16)));
		this.teamExists = false;
		this.sent = false;
	}

	public void createPrefixAndSuffix(String prefix, String suffix) {
		if (toRemove || teamExists)
			return;
		this.teamExists = true;
		this.prefix = Util.fixColor(prefix.substring(0, Math.min(prefix.length(), 16)));
		this.suffix = Util.fixColor(suffix.substring(0, Math.min(suffix.length(), 16)));
		PacketUtil.sendPacket(this.tab.getPlayer(), TabUtil.createTeamPacket(name, name, this.prefix, this.suffix, 0, name));
	}

	public void updatePrefixAndSuffix(String prefix, String suffix) {
		if (toRemove || !teamExists)
			return;
		this.prefix = Util.fixColor(prefix.substring(0, Math.min(prefix.length(), 16)));
		this.suffix = Util.fixColor(suffix.substring(0, Math.min(suffix.length(), 16)));
		PacketUtil.sendPacket(this.tab.getPlayer(), TabUtil.createTeamPacket(name, name, this.prefix, this.suffix, 2, name));
	}

	public void removePrefixAndSuffix() {
		if (toRemove || !teamExists)
			return;
		this.teamExists = false;
		PacketUtil.sendPacket(this.tab.getPlayer(), TabUtil.createTeamPacket(name, name, null, null, 1, name));
	}

}
