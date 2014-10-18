package net.karolek.revoguild.utils;

import net.karolek.revoguild.utils.Reflection.FieldAccessor;
import net.karolek.revoguild.utils.Reflection.MethodInvoker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PacketUtil {

	public static void sendPacket(Player player, Object...objects) {
		Class<?> packetClass = Reflection.getMinecraftClass("Packet");
		Object handle = getHandle(player);
		FieldAccessor<Object> f = Reflection.getSimpleField(handle.getClass(), "playerConnection");
		MethodInvoker m = Reflection.getMethod(f.get(handle).getClass(), "sendPacket", packetClass);
		for(Object o : objects) 
			m.invoke(f.get(handle), o);
	}
	
	public static Object getHandle(Entity en) {
		return Reflection.getMethod(en.getClass(), "getHandle").invoke(en);
	}
	
}
