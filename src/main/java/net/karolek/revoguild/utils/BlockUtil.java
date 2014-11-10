package net.karolek.revoguild.utils;

import net.karolek.revoguild.utils.Reflection.FieldAccessor;

public class BlockUtil {

    private static FieldAccessor<Float> field = Reflection.getField(Reflection.getMinecraftClass("Block"), "durability", Float.TYPE);

    public static void setDurability(String name, float durability) {

        FieldAccessor<Object> f = Reflection.getSimpleField(Reflection.getMinecraftClass("Blocks"), name.toUpperCase());

        field.set(f.get(null), durability);

        Logger.info("Durability of " + name + " was changed to " + durability);

    }

}
