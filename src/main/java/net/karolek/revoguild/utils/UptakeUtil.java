package net.karolek.revoguild.utils;

import java.util.HashMap;

import lombok.Getter;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.managers.GuildManager;
import net.karolek.revoguild.utils.Reflection.ConstructorInvoker;
import net.karolek.revoguild.utils.Reflection.FieldAccessor;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class UptakeUtil {

	@Getter
	private static final HashMap<String, HashMap<Guild, Integer>>	sends								= new HashMap<>();

	// private static int count = 0;

	private static Class<?>														spawnEntityClass				= Reflection.getMinecraftClass("PacketPlayOutSpawnEntity");
	private static Class<?>														entityDestroyClass			= Reflection.getMinecraftClass("PacketPlayOutEntityDestroy");
	private static ConstructorInvoker										entityDestroyConstructor	= Reflection.getConstructor(entityDestroyClass);
	private static FieldAccessor<int[]>										entityDestroyA					= Reflection.getField(entityDestroyClass, "a", int[].class);
	private static ConstructorInvoker										spawnEntityConstructor		= Reflection.getConstructor(spawnEntityClass);
	private static FieldAccessor<Integer>									entityCount						= Reflection.getField(Reflection.getMinecraftClass("Entity"), "entityCount", int.class);

	private static FieldAccessor<Integer>									spawnEntityA					= Reflection.getField(spawnEntityClass, "a", int.class);
	private static FieldAccessor<Integer>									spawnEntityB					= Reflection.getField(spawnEntityClass, "b", int.class);
	private static FieldAccessor<Integer>									spawnEntityC					= Reflection.getField(spawnEntityClass, "c", int.class);
	private static FieldAccessor<Integer>									spawnEntityD					= Reflection.getField(spawnEntityClass, "d", int.class);
	private static FieldAccessor<Integer>									spawnEntityE					= Reflection.getField(spawnEntityClass, "e", int.class);
	private static FieldAccessor<Integer>									spawnEntityF					= Reflection.getField(spawnEntityClass, "f", int.class);
	private static FieldAccessor<Integer>									spawnEntityG					= Reflection.getField(spawnEntityClass, "g", int.class);
	private static FieldAccessor<Integer>									spawnEntityH					= Reflection.getField(spawnEntityClass, "h", int.class);
	private static FieldAccessor<Integer>									spawnEntityI					= Reflection.getField(spawnEntityClass, "i", int.class);
	private static FieldAccessor<Integer>									spawnEntityJ					= Reflection.getField(spawnEntityClass, "j", int.class);
	private static FieldAccessor<Integer>									spawnEntityK					= Reflection.getField(spawnEntityClass, "k", int.class);

	private static Object createSpawnPacket(int id, double x, double y, double z, int type) {
		Object o = spawnEntityConstructor.invoke();
		spawnEntityA.set(o, id);
		spawnEntityB.set(o, floor(x * 32.0D));
		spawnEntityC.set(o, floor(y * 32.0D));
		spawnEntityD.set(o, floor(z * 32.0D));
		spawnEntityE.set(o, 0);
		spawnEntityF.set(o, 0);
		spawnEntityG.set(o, 0);
		spawnEntityH.set(o, 0);
		spawnEntityI.set(o, 0);
		spawnEntityJ.set(o, type);
		spawnEntityK.set(o, 0);
		return o;
	}

	private static Object createDespawnPacket(int id) {
		Object o = entityDestroyConstructor.invoke();
		entityDestroyA.set(o, new int[] { id });
		return o;
	}

	private static int getId() {
		int id = entityCount.get(null);
		entityCount.set(null, id + 1);
		return id;
	}

	public static int getId(Player p, Guild g) {
		return sends.get(p.getName()).get(g);
	}

	public static boolean hasId(Player p, int id) {
		HashMap<Guild, Integer> ids = sends.get(p.getName());
		if (ids == null)
			return false;
		for (int v : ids.values())
			if (v == id)
				return true;
		return false;
	}

	public static void respawnGuild(Guild g) {
		for (Player p : Util.getOnlinePlayers())
			respawnGuild(p, g);
	}

	public static void despawnGuild(Guild g) {
		for (Player p : Util.getOnlinePlayers())
			despawnGuild(p, g);
	}

	public static void init() {
		for (Player p : Util.getOnlinePlayers())
			init(p);
	}

	public static void init(Player p) {
		for (Guild g : GuildManager.getGuilds().values())
			respawnGuild(p, g);
	}

	public static void respawnGuild(Player p, Guild g) {
		HashMap<Guild, Integer> ids = sends.get(p.getName());
		if (ids == null)
			ids = new HashMap<>();

		Location l = g.getCuboid().getCenter();
		l.add(0.5, 1.0, 0.5);

		double x = l.getX();
		double y = l.getY();
		double z = l.getZ();

		if (ids.containsKey(g)) {
			int actualId = ids.get(g);
			int newId = getId();
			PacketUtil.sendPacket(p, createDespawnPacket(actualId));
			PacketUtil.sendPacket(p, createSpawnPacket(newId, x, y, z, 51));
			ids.put(g, newId);
		} else {
			int newId = getId();
			PacketUtil.sendPacket(p, createSpawnPacket(newId, x, y, z, 51));
			ids.put(g, newId);
		}
		sends.put(p.getName(), ids);

	}

	public static void despawnGuild(Player p, Guild g) {
		HashMap<Guild, Integer> ids = sends.get(p.getName());
		if (ids == null)
			ids = new HashMap<>();
		if (!ids.containsKey(g))
			return;
		int id = ids.get(g);
		ids.remove(g);
		sends.put(p.getName(), ids);
		PacketUtil.sendPacket(p, createDespawnPacket(id));
	}

	private static int floor(double paramDouble) {
		int i = (int) paramDouble;
		return paramDouble < i ? i - 1 : i;
	}

}
