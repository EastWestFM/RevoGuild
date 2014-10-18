package net.karolek.revoguild.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Util {

	public static boolean sendMsg(CommandSender sender, String message, String permission) {
		if (permission != null)
			if (!sender.hasPermission(permission))
				sender.sendMessage(fixColor(message));
		sender.sendMessage(fixColor(message));
		return true;
	}

	public static boolean sendMsg(CommandSender sender, String message) {
		return sendMsg(sender, message, null);
	}

	public static boolean sendMsg(Collection<? extends CommandSender> senders, String message, String permission) {
		sendMsg(Bukkit.getConsoleSender(), message, permission);
		for (CommandSender sender : senders)
			sendMsg(sender, message, permission);
		return true;
	}

	public static boolean sendMsg(Collection<? extends CommandSender> senders, String message) {
		for (CommandSender sender : senders)
			sendMsg(sender, message, null);
		return true;
	}

	public static String fixColor(String string) {
		return ChatColor.translateAlternateColorCodes('&', string);
	}

	public static List<String> fixColor(List<String> strings) {
		List<String> colors = new ArrayList<String>();
		for (String s : strings)
			colors.add(fixColor(s));
		return colors;
	}

	public static String[] fixColor(String[] strings) {
		for (int i = 0; i < strings.length; i++)
			strings[i] = fixColor(strings[i]);
		return strings;
	}

	public static Collection<? extends Player> getOnlinePlayers() {
		return Arrays.asList(Bukkit.getOnlinePlayers());
	}

	public static Player getDamager(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		if (damager instanceof Player) {
			return ((Player) damager);
		} else if (damager instanceof Projectile) {
			Projectile p = (Projectile) damager;
			if (p instanceof Player)
				return (Player) p.getShooter();
		}
		return null;
	}

	public static void copy(InputStream in, File file) {
		try {
			OutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			out.close();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String secondsToString(int seconds) {
		LinkedHashMap<Integer, String> values = new LinkedHashMap<Integer, String>(6);

		values.put(60 * 60 * 24 * 30 * 12, "y");
		values.put(60 * 60 * 24 * 30, "msc");
		values.put(60 * 60 * 24, "d");
		values.put(60 * 60, "h");
		values.put(60, "min");
		values.put(1, "s");

		String[] v = new String[6];

		int i = 0;
		for (Entry<Integer, String> e : values.entrySet()) {
			int iDiv = seconds / e.getKey();
			if (iDiv >= 1) {
				int x = (int) Math.floor(iDiv);
				v[i] = x + e.getValue();
				seconds -= x * e.getKey();
			}
			++i;
		}

		return StringUtils.join(v, " ");
	}

	public static String getDate(long time) {
		return new SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(time));
	}

	public static boolean containsIgnoreCase(String[] array, String element) {
		for (String s : array)
			if (s.equalsIgnoreCase(element))
				return true;
		return false;
	}

	public static boolean isAlphaNumeric(String s) {
		return s.matches("^[a-zA-Z0-9_]*$");
	}

	public static boolean isFloat(String string) {
		return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
	}

	public static boolean isInteger(String string) {
		return (Pattern.matches("-?[0-9]+", string.subSequence(0, string.length())));
	}

	public static Location getLocation(String world, int x, int y, int z) {
		return new Location(Bukkit.getWorld(world), x, y, z);
	}

	public static Location getLocation(String world, double x, double y, double z, float yaw, float pitch) {
		return new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
	}

	public static long parseDateDiff(String time, boolean future) {
		try {
			Pattern timePattern = Pattern.compile("(?:([0-9]+)\\s*y[a-z]*[,\\s]*)?(?:([0-9]+)\\s*mo[a-z]*[,\\s]*)?(?:([0-9]+)\\s*w[a-z]*[,\\s]*)?(?:([0-9]+)\\s*d[a-z]*[,\\s]*)?(?:([0-9]+)\\s*h[a-z]*[,\\s]*)?(?:([0-9]+)\\s*m[a-z]*[,\\s]*)?(?:([0-9]+)\\s*(?:s[a-z]*)?)?", 2);
			Matcher m = timePattern.matcher(time);
			int years = 0;
			int months = 0;
			int weeks = 0;
			int days = 0;
			int hours = 0;
			int minutes = 0;
			int seconds = 0;
			boolean found = false;
			while (m.find()) {
				if ((m.group() != null) && (!m.group().isEmpty())) {
					for (int i = 0; i < m.groupCount(); i++)
						if ((m.group(i) != null) && (!m.group(i).isEmpty())) {
							found = true;
							break;
						}
					if (found) {
						if ((m.group(1) != null) && (!m.group(1).isEmpty()))
							years = Integer.parseInt(m.group(1));
						if ((m.group(2) != null) && (!m.group(2).isEmpty()))
							months = Integer.parseInt(m.group(2));
						if ((m.group(3) != null) && (!m.group(3).isEmpty()))
							weeks = Integer.parseInt(m.group(3));
						if ((m.group(4) != null) && (!m.group(4).isEmpty()))
							days = Integer.parseInt(m.group(4));
						if ((m.group(5) != null) && (!m.group(5).isEmpty()))
							hours = Integer.parseInt(m.group(5));
						if ((m.group(6) != null) && (!m.group(6).isEmpty()))
							minutes = Integer.parseInt(m.group(6));
						if ((m.group(7) == null) || (m.group(7).isEmpty()))
							break;
						seconds = Integer.parseInt(m.group(7));
						break;
					}
				}
			}
			if (!found) { return -1L; }
			Calendar c = new GregorianCalendar();
			if (years > 0)
				c.add(1, years * (future ? 1 : -1));
			if (months > 0)
				c.add(2, months * (future ? 1 : -1));
			if (weeks > 0)
				c.add(3, weeks * (future ? 1 : -1));
			if (days > 0)
				c.add(5, days * (future ? 1 : -1));
			if (hours > 0)
				c.add(11, hours * (future ? 1 : -1));
			if (minutes > 0)
				c.add(12, minutes * (future ? 1 : -1));
			if (seconds > 0)
				c.add(13, seconds * (future ? 1 : -1));
			Calendar max = new GregorianCalendar();
			max.add(1, 10);
			if (c.after(max)) { return max.getTimeInMillis(); }
			return c.getTimeInMillis();
		} catch (Exception e) {}
		return -1L;
	}

	public static void giveItems(Player p, ItemStack... items) {
		Inventory i = p.getInventory();
		HashMap<Integer, ItemStack> notStored = i.addItem(items);
		for (Entry<Integer, ItemStack> e : notStored.entrySet())
			p.getWorld().dropItemNaturally(p.getLocation(), (ItemStack) e.getValue());
	}

}
