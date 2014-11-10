package net.karolek.revoguild.tasks;

import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.utils.Logger;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Updater extends BukkitRunnable {

    @Override
    public void run() {
        try {
            String url = "https://raw.githubusercontent.com/EastWestFM/RevoGuild/master/version.txt";

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String version = br.readLine();
            String myVersion = GuildPlugin.getPlugin().getDescription().getVersion();
            int build = Integer.parseInt(version.split("-")[1].replace("b", ""));
            int myBuild = Integer.parseInt(myVersion.split("-")[1].replace("b", ""));
            if (!myVersion.equalsIgnoreCase(version) && build > myBuild) {
                Logger.info("-------------[ RevoGUILD ]-------------");
                Logger.info(" > Znaleziono nowa wersje pluginu!", "");
                Logger.info(" > Zainstalowana wersja: " + myVersion);
                Logger.info(" > Aktualna wersja: " + version, "");
                Logger.info(" > Pobierz najnowsza wersje z: https://github.com/EastWestFM/RevoGuild/releases");
                Logger.info("---------------------------------------");
            }
            conn.disconnect();
        } catch (Exception e) {
            Logger.warning("Blad podczas sprawdzania aktualizacji");
        }
    }
}
