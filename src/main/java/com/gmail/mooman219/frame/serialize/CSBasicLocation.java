package com.gmail.mooman219.frame.serialize;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs(value = "CSBasicLocation")
public class CSBasicLocation implements ConfigurationSerializable{
    private final String world;
    private final String uuid;
    private final int x;
    private final int y;
    private final int z;

    private transient WeakReference<Location> weakLoc;

    public CSBasicLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.uuid = loc.getWorld().getUID().toString();
        this.x = loc.getBlockX();
        this.y = loc.getBlockY();
        this.z = loc.getBlockZ();
    }

    public CSBasicLocation(Map<String, Object> map) {
        this.world = (String) map.get("world");
        this.uuid = (String) map.get("uuid");
        this.x = (Integer) map.get("x");
        this.y = (Integer) map.get("y");
        this.z = (Integer) map.get("z");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = CSBasicLocation.class.hashCode();
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((world == null) ? 0 : world.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CSBasicLocation other = (CSBasicLocation) obj;
        if (uuid == null) {
            if (other.uuid != null)
                return false;
        } else if (!uuid.equals(other.uuid))
            return false;
        if (world == null) {
            if (other.world != null)
                return false;
        } else if (!world.equals(other.world))
            return false;
        if (x != other.x)
            return false;
        if (y != other.y)
            return false;
        if (z != other.z)
            return false;
        return true;
    }

    public static CSBasicLocation deserialize(Map<String, Object> map) {
        return new CSBasicLocation(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("world", this.world);
        map.put("uuid", this.uuid);
        map.put("x", this.x);
        map.put("y", this.y);
        map.put("z", this.z);
        return map;
    }

    public final Location getLocation() {
        if (weakLoc == null || weakLoc.get() == null) {
            World world = Bukkit.getWorld(this.uuid);
            if (world == null) {
                world = Bukkit.getWorld(this.world);
                if (world == null) {
                    throw new IllegalStateException("Cannot find world by UUID or name");
                }
            }
            weakLoc = new WeakReference<Location>(new Location(world, x, y, z));
        }
        return weakLoc.get();
    }
}
