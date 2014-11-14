package net.karolek.revoguild.managers;

import lombok.Getter;
import net.karolek.revoguild.GuildPlugin;
import net.karolek.revoguild.base.Guild;
import net.karolek.revoguild.base.User;
import net.karolek.revoguild.data.Config;
import net.karolek.revoguild.tablist.update.TabThread;
import net.karolek.revoguild.utils.Logger;
import net.karolek.revoguild.utils.SpaceUtil;
import net.karolek.revoguild.utils.UptakeUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GuildManager {

    @Getter
    private static final Map<String, Guild> guilds = new HashMap<String, Guild>();

    public static Guild createGuild(String tag, String name, Player owner) {
        Guild g = new Guild(tag, name, owner);
        g.insert();
        User u = UserManager.getUser(owner);
        g.addInvite(u);
        g.addMember(u);
        g.addTreasureUser(u);
        guilds.put(g.getTag().toUpperCase(), g);
        NameTagManager.createGuild(g, owner);
        setGuildRoom(g);
        owner.teleport(g.getCuboid().getCenter());
        g.setHome(g.getCuboid().getCenter());
        g.update(false);
        TabThread.restart();
        UptakeUtil.respawnGuild(g);
        return g;
    }

    public static void removeGuild(Guild g) {
        g.delete();
        removeGuildRoom(g);
        NameTagManager.removeGuild(g);
        guilds.remove(g.getTag().toUpperCase());
        TabThread.restart();
        UptakeUtil.despawnGuild(g);
    }

    public static Guild getGuild(String tag) {
        for (Guild g : guilds.values())
            if ((g.getTag().equalsIgnoreCase(tag)) || (g.getName().equalsIgnoreCase(tag)))
                return g;
        return null;
    }

    public static Guild getGuild(Location loc) {
        for (Guild g : guilds.values())
            if (g.getCuboid().isInCuboid(loc))
                return g;
        return null;
    }

    public static Guild getGuild(Player p) {
        User u = UserManager.getUser(p);
        for (Guild g : guilds.values())
            if (g.isMember(u))
                return g;
        return null;
    }

    public static boolean canCreateGuild(Location loc) {
        if (!loc.getWorld().getName().equals(Config.CUBOID_WORLD))
            return false;

        int spawnX = loc.getWorld().getSpawnLocation().getBlockX();
        int spawnZ = loc.getWorld().getSpawnLocation().getBlockZ();

        if (Config.CUBOID_SPAWN_ENBLAED)
            if (Math.abs(loc.getBlockX() - spawnX) < Config.CUBOID_SPAWN_DISTANCE && Math.abs(loc.getBlockZ() - spawnZ) < Config.CUBOID_SPAWN_DISTANCE)
                return false;


        // if (Config.CUBOID_SPAWN_ENBLAED)
        //      if ((Math.abs(loc.getBlockX()) < Config.CUBOID_SPAWN_DISTANCE) && (Math.abs(loc.getBlockZ()) < Config.CUBOID_SPAWN_DISTANCE))
        //        return false;

        int mindistance = Config.CUBOID_SIZE_MAX * 2 + Config.CUBOID_SIZE_BETWEEN;
        for (Guild g : guilds.values())
            if ((Math.abs(g.getCuboid().getCenterX() - loc.getBlockX()) <= mindistance) && (Math.abs(g.getCuboid().getCenterZ() - loc.getBlockZ()) <= mindistance))
                return false;
        return true;
    }

    public static void setGuildRoom(Guild g) {
        Location c = g.getCuboid().getCenter();
        c.setY(59);
        for (Location loc : SpaceUtil.getSquare(c, 4, 3))
            loc.getBlock().setType(Material.AIR);
        for (Location loc : SpaceUtil.getSquare(c, 4))
            loc.getBlock().setType(Material.OBSIDIAN);
        for (Location loc : SpaceUtil.getCorners(c, 4, 3))
            loc.getBlock().setType(Material.OBSIDIAN);
        c.add(0, 4, 0);
        for (Location loc : SpaceUtil.getWalls(c, 4))
            loc.getBlock().setType(Material.OBSIDIAN);
        //g.getCuboid().getCenter().getBlock().setType(Material.BEDROCK);
    }

    public static void removeGuildRoom(Guild g) {
        Location c = g.getCuboid().getCenter();
        c.setY(59);
        for (Location loc : SpaceUtil.getSquare(c, 4, 4))
            loc.getBlock().setType(Material.AIR);
    }

    public static void enable() {
        ResultSet rs = GuildPlugin.getStore().query("SELECT * FROM `{P}guilds`");
        try {
            while (rs.next()) {
                Guild g = new Guild(rs);
                guilds.put(g.getTag().toUpperCase(), g);
            }
            Logger.info("Loaded " + guilds.size() + " guilds!");
        } catch (SQLException e) {
            Logger.warning("An error occurred while loading guilds!", "Error: " + e.getMessage());
            Logger.exception(e);
        }
    }

}
