package net.karolek.revoguild.managers;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.tablist.update.RankList;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Logger;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class UserManager {

    @Getter
    private static final Map<String, User> users = new HashMap<>();

    public static User getUser(OfflinePlayer p) {
        for (User u : users.values()) {
            if (Config.USEUUID) {
                if (p.getUniqueId().equals(u.getUuid()))
                    return u;
            } else {
                if (u.getUuid() == null && u.getName().equalsIgnoreCase(p.getName()))
                    return u;
            }
        }
        return null;
    }

    public static int getPosition(User user) {
        for (RankList.Data<User> userData : TabThread.getInstance().getRankList().getTopPlayers())
            if (userData.getKey().equals(user))
                return TabThread.getInstance().getRankList().getTopPlayers().indexOf(userData)+1;
        return -1;
    }

    private static User getOfflineUser(OfflinePlayer p) {
        return getUser(p);
    }

    public static User getUser(Player p) {
        if (p.hasMetadata("user")) {
            User user = null;
            for (MetadataValue mv : p.getMetadata("user")) {
                if (!mv.getOwningPlugin().getName().equalsIgnoreCase("RevoGuild"))
                    continue;
                Object v = mv.value();
                if (v instanceof User)
                    user = (User) v;
            }
            return (user == null) ? getOfflineUser(p) : user;
        }
        return getOfflineUser(p);
    }

    public static User getUser(String s) {
        return users.get(s);
    }

    public static User getUserByName(String s) {
        for (User u : users.values())
            if (u.getName().equalsIgnoreCase(s))
                return u;
        return null;
    }

    public static User createUser(Player p) {
        User u = new User(p);
        u.insert();
        users.put(u.toString(), u);
        return u;
    }

    public static void initUser(Player p) {
        p.setMetadata("user", new FixedMetadataValue(GuildPlugin.getPlugin(), getUser(p)));
    }

    public static void enable() {
        ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}users`");
        try {
            while (rs.next()) {
                User u = new User(rs);
                users.put(u.toString(), u);
            }
            Logger.info("Loaded " + users.size() + " users!");
        } catch (SQLException e) {
            Logger.warning("An error occurred while loading users!", "Error: " + e.getMessage());
            Logger.exception(e);
        }
    }
}
