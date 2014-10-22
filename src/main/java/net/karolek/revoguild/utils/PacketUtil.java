package net.karolek.revoguild.utils;

import net.karolek.revoguild.utils.Reflection.FieldAccessor;
import net.karolek.revoguild.utils.Reflection.MethodInvoker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PacketUtil {

	private static MethodInvoker				handleMethod		= Reflection.getMethod(Entity.class, "getHandle");
	private static MethodInvoker				sendPacket			= Reflection.getMethod(Reflection.getMinecraftClass("PlayerConnection"), "sendPacket", Reflection.getMinecraftClass("Packet"));
	private static FieldAccessor<Object>	playerConnection	= Reflection.getSimpleField(Reflection.getMinecraftClass("EntityPlayer"), "playerConnection");

	public static void sendPacket(Player player, Object... objects) {
		Object handle = handleMethod.invoke(player);
		for (Object o : objects)
			sendPacket.invoke(playerConnection.get(handle), o);
	}

}
