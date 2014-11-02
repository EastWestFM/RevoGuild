package net.karolek.revoguild.packetlistener.events;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
@Setter
public class PacketSendEvent extends Event implements Cancellable {

	private static final HandlerList	handlers	= new HandlerList();
	private Object							packet;
	private final Player					player;
	private boolean						cancelled;

	public PacketSendEvent(Object packet, Player player) {
		super(true);
		this.packet = packet;
		this.player = player;
		this.cancelled = false;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	public String getPacketName() {
		return this.packet.getClass().getSimpleName();
	}

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

}
