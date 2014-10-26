package net.karolek.revoguild.tablist;

import java.util.HashMap;

import net.karolek.revoguild.manager.Manager;
import net.karolek.revoguild.utils.PacketUtil;
import net.karolek.revoguild.utils.Util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tab {

	private final Player								player;
	private final HashMap<Integer, TabCell>	slots			= new HashMap<>();
	private final HashMap<Integer, TabCell>	toRemove		= new HashMap<>();
	private int											defaultPing	= 1000;

	public Tab(Player p) {
		this.player = p;
		this.firstClear();
	}

	public void clearSlot(int slot) {
		TabCell TabCell = slots.remove(slot);
		if (TabCell == null) { return; }
		TabCell.toRemove = true;
		toRemove.put(slot, TabCell);
	}

	public TabCell setSlot(int slot, String name) {
		TabCell TabCell = new TabCell(this, name);
		slots.put(slot, TabCell);
		return TabCell;
	}

	public TabCell setSlot(int slot, String prefix, String name, String suffix) {
		TabCell TabCell = new TabCell(this, prefix, name, suffix, defaultPing);
		slots.put(slot, TabCell);
		return TabCell;
	}
	
	public TabCell getSlot(int slot) {
		return slots.get(slot);
	}

	public void send() {
		for (int i = 1; i <= 60; i++) {
			TabCell slot = slots.get(i);
			if (slot != null) {
				slot.sent = true;
				PacketUtil.sendPacket(player, Manager.TAB.createPlayerPacket(slot.getName(), true, slot.getPing()));
				if (slot.teamExists)
					PacketUtil.sendPacket(player, Manager.TAB.createTeamPacket(slot.getName(), slot.getName(), slot.getPrefix(), slot.getSuffix(), 0, slot.getName()));

			} else {
				String nullName = "§" + String.valueOf(i);

				if (i >= 10)
					nullName = "§" + String.valueOf(i / 10) + "§" + String.valueOf(i % 10);

				
				PacketUtil.sendPacket(player, Manager.TAB.createPlayerPacket(nullName, true, this.defaultPing));

			}
		}
	}

	public void update() {
		clear();
		send();
	}

	public void firstClear() {
		for (Player p : Util.getOnlinePlayers())
			PacketUtil.sendPacket(player, Manager.TAB.createPlayerPacket(p.getName(), false, this.defaultPing));
	}

	public void clear() {
		for (int i = 1; i <= 60; i++) {
			TabCell slot = toRemove.get(i);
			if (slot == null) {
				slot = slots.get(i);
			}
			if (slot != null) {
				slot.sent = false;
				if (slot.teamExists)
					PacketUtil.sendPacket(player, Manager.TAB.createTeamPacket(slot.getName(), slot.getName(), null, null, 1, slot.getName()));
				PacketUtil.sendPacket(player, Manager.TAB.createPlayerPacket(slot.getName(), false, slot.getPing()));
			} else {

				String nullName = "§" + String.valueOf(slot);
				if (i >= 10)
					nullName = "§" + String.valueOf(i / 10) + "§" + String.valueOf(i % 10);

				PacketUtil.sendPacket(player, Manager.TAB.createPlayerPacket(nullName, false, defaultPing));
			}
		}
		toRemove.clear();
	}

	public static char[] base(int n) {
		String hex = Integer.toHexString(n + 1);
		char[] alloc = new char[hex.length() * 2];
		for (int i = 0; i < alloc.length; i++) {
			if (i % 2 == 0) {
				alloc[i] = ChatColor.COLOR_CHAR;
			} else {
				alloc[i] = hex.charAt(i / 2);
			}
		}
		return alloc;
	}

}
