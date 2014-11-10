package net.karolek.revoguild.base;

import lombok.Getter;
import lombok.Setter;
import net.karolek.revoguild.data.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

@Getter
@Setter
public class Cuboid {

    private final World world;
    private final int centerX;
    private final int centerZ;
    private int size;

    public Cuboid(String world, int x, int z, int size) {
        this.world = Bukkit.getWorld(world);
        this.centerX = x;
        this.centerZ = z;
        this.size = size;
    }

    public Location getCenter() {
        return new Location(this.world, this.centerX, 60.0D, this.centerZ);
    }

    public boolean addSize() {
        if (this.size >= Config.CUBOID_SIZE_MAX)
            return false;
        this.size += Config.CUBOID_SIZE_ADD;
        return true;
    }

    public boolean isInCuboid(Location loc) {
        if (!loc.getWorld().equals(this.world))
            return false;
        int distancex = Math.abs(loc.getBlockX() - this.centerX);
        int distancez = Math.abs(loc.getBlockZ() - this.centerZ);
        return (distancex <= this.size) && (distancez <= this.size);
    }

    public boolean inInCuboid(Player p) {
        return isInCuboid(p.getLocation());
    }

    public boolean inInCuboid(Block b) {
        return isInCuboid(b.getLocation());
    }

}
