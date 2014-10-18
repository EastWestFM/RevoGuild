package net.karolek.revoguild.data;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.utils.Util;

public class Lang {

	private static final String prefix = "lang.";
	private static File file = new File(GuildPlugin.getPlugin().getDataFolder(), "lang.yml");
	private static FileConfiguration c = null;
	
	public static void loadLang() {
		try {
			
			if (!file.exists()) {
				file.getParentFile().mkdirs();
				InputStream is = GuildPlugin.getPlugin().getResource(file.getName());
				if(is != null)
					Util.copy(is, file);
			}
			
			c = YamlConfiguration.loadConfiguration(file);

			for (Field f : Lang.class.getFields()) {

				if (c.isSet(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")))
					f.set(null, c.get(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", ".")));
				System.out.println(f.getName() + " -> " + f.get(null));

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveLang() {
		try {
			FileConfiguration c = GuildPlugin.getPlugin().getConfig();
			for (Field f : Lang.class.getFields()) 
				c.set(prefix + f.getName().toLowerCase().replaceFirst("_", ",").replace(",", "."), f.get(null));
			
			c.save(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void reloadLang() {
		loadLang();
		saveLang();
	}
	
}
