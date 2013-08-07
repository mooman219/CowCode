package com.gmail.mooman219.frame.serialize.jack;

import java.lang.ref.WeakReference;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JacksonData;

public class FastLocation implements JacksonData {
    private static final long serialVersionUID = 5223854976659448343L;
    private String world;
    private UUID uuid;
    private int x;
    private int y;
    private int z;

    private transient WeakReference<Location> weakLocation;

    protected FastLocation() {}

    public FastLocation(Location location) {
        this.world = location.getWorld().getName();
        this.uuid = location.getWorld().getUID();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    /**
     * Getters and Setters
     */

    public String getWorld() {
        return world;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    /**
     * Misc functions
     */

    public Location toLocation() {
        if (weakLocation == null || weakLocation.get() == null) {
            World world = Bukkit.getWorld(this.uuid);
            if (world == null) {
                world = Bukkit.getWorld(this.world);
                if (world == null) {
                    throw new IllegalStateException("Cannot find world by UUID or name");
                }
            }
            this.uuid = world.getUID();
            this.world = world.getName();
            weakLocation = new WeakReference<Location>(new Location(world, x, y, z));
        }
        return weakLocation.get();
    }

    /**
     * Serialization and Deserialization
     */

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastLocation deserialize(String data) {
        return JsonHelper.fromJackson(data, FastLocation.class);
    }

    /**
     * HashCode and Equals
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((world == null) ? 0 : world.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        FastLocation other = (FastLocation) obj;
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        if (world == null) {
            if (other.world != null) {
                return false;
            }
        } else if (!world.equals(other.world)) {
            return false;
        }
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }
}
