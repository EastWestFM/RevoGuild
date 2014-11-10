package net.karolek.revoguild.tablist;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.utils.PacketUtil;
import net.karolek.revoguild.utils.Util;
import org.bukkit.entity.Player;

import java.util.HashMap;

@Getter
@Setter
public class Tab {

    private final Player player;
    private final HashMap<Integer, TabCell> slots = new HashMap<>();
    private final HashMap<Integer, TabCell> toRemove = new HashMap<>();
    private int defaultPing = 1000;

    public Tab(Player p) {
        this.player = p;
        this.firstClear();
    }

    public void clearSlot(int slot) {
        TabCell cell = slots.remove(slot);
        if (cell == null) {
            return;
        }
        cell.toRemove = true;
        toRemove.put(slot, cell);
    }

    public TabCell setSlot(int slot, String name) {
        TabCell cell = new TabCell(this, name);
        slots.put(slot, cell);
        return cell;
    }

    public TabCell setSlot(int slot, String prefix, String name, String suffix) {
        TabCell cell = new TabCell(this, prefix, name, suffix, defaultPing);
        slots.put(slot, cell);
        return cell;
    }

    public TabCell getSlot(int slot) {
        return slots.get(slot);
    }

    public void send() {
        for (int i = 1; i <= 60; i++) {
            TabCell slot = slots.get(i);
            if (slot != null) {
                slot.sent = true;
                PacketUtil.sendPacket(player, TabUtil.createPlayerPacket(slot.getName(), true, slot.getPing()));
                if (slot.teamExists)
                    PacketUtil.sendPacket(player, TabUtil.createTeamPacket(slot.getName(), slot.getName(), slot.getPrefix(), slot.getSuffix(), 0, slot.getName()));

            } else {
                String nullName = "§" + String.valueOf(i);
                if (i >= 10)
                    nullName = "§" + String.valueOf(i / 10) + "§" + String.valueOf(i % 10);

                PacketUtil.sendPacket(player, TabUtil.createPlayerPacket(nullName, true, this.defaultPing));
            }
        }
    }

    public void update() {
        clear();
        send();
    }

    public void firstClear() {
        for (Player p : Util.getOnlinePlayers())
            PacketUtil.sendPacket(player, TabUtil.createPlayerPacket(p.getName(), false, this.defaultPing));
    }

    public void clear() {
        for (int i = 1; i <= 60; i++) {
            TabCell slot = toRemove.get(i);
            if (slot == null)
                slot = slots.get(i);
            if (slot != null) {
                slot.sent = false;
                if (slot.teamExists)
                    PacketUtil.sendPacket(player, TabUtil.createTeamPacket(slot.getName(), slot.getName(), null, null, 1, slot.getName()));
                PacketUtil.sendPacket(player, TabUtil.createPlayerPacket(slot.getName(), false, slot.getPing()));
            } else {
                String nullName = "§" + String.valueOf(slot);
                if (i >= 10)
                    nullName = "§" + String.valueOf(i / 10) + "§" + String.valueOf(i % 10);
                PacketUtil.sendPacket(player, TabUtil.createPlayerPacket(nullName, false, defaultPing));
            }
        }
        toRemove.clear();
    }

}
