package net.karolek.revoguild.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Items {

	public static ItemStack getItem(Material m, int a, String name, String... lore) {
		ItemStack item = new ItemStack(m, a);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Util.fixColor(name));
		if (lore != null)
			meta.setLore(Util.fixColor(Arrays.asList(lore)));
		item.setItemMeta(meta);
		return item;
	}

	@SuppressWarnings("deprecation")
	public static List<ItemStack> getItems(String string, int modifier) {
		List<ItemStack> items = new ArrayList<ItemStack>();
		for (String s : string.split(";")) {
			String[] split = s.split("-");
			int id = Integer.parseInt(split[0].split(":")[0]);
			int data = Integer.parseInt(split[0].split(":")[1]);
			int amount = Integer.parseInt(split[1]) * modifier;
			items.add(new ItemStack(Material.getMaterial(id), amount, (short) data));
		}
		return items;
	}

	public static boolean checkItems(List<ItemStack> items, Player p) {
		for (ItemStack item : items)
			if (!p.getInventory().containsAtLeast(item, item.getAmount()))
				return false;
		return true;
	}

	public static void removeItems(List<ItemStack> items, Player player) {
		Inventory inv = player.getInventory();
		List<ItemStack> removes = new ArrayList<ItemStack>();
		for (ItemStack item : items)
			if (inv.containsAtLeast(item, item.getAmount()))
				removes.add(item);
		if (removes.size() == items.size())
			for (ItemStack item : items)
				for (ItemStack remove : removes)
					if (item.getType().equals(remove.getType()) && item.getData().equals(remove.getData()))
						inv.removeItem(item);
		removes.clear();
	}

	public static boolean checkAndRemove(List<ItemStack> items, Player player) {
		boolean has = checkItems(items, player);
		if (has)
			removeItems(items, player);
		return has;
	}

}
