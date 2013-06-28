package com.gmail.mooman219.frame.serialize.yaml;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs(value = "CSLocation")
public class CSLocation implements ConfigurationSerializable {
    private final String world;
    private final String uuid;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    private transient WeakReference<Location> weakLoc;

    public CSLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.uuid = loc.getWorld().getUID().toString();
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
    }

    public CSLocation(Map<String, Object> map) {
        this.world = (String) map.get("world");
        this.uuid = (String) map.get("uuid");
        this.x = (Double) map.get("x");
        this.y = (Double) map.get("y");
        this.z = (Double) map.get("z");
        this.yaw = ((Double) map.get("yaw")).floatValue();
        this.pitch = ((Double) map.get("pitch")).floatValue();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = CSLocation.class.hashCode();
        result = prime * result + Float.floatToIntBits(pitch);
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((world == null) ? 0 : world.hashCode());
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + Float.floatToIntBits(yaw);
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        CSLocation other = (CSLocation) obj;
        if (Float.floatToIntBits(pitch) != Float.floatToIntBits(other.pitch)) {
            return false;
        }
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
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Float.floatToIntBits(yaw) != Float.floatToIntBits(other.yaw)) {
            return false;
        }
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }

    public static CSLocation deserialize(Map<String, Object> map) {
        return new CSLocation(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("world", this.world);
        map.put("uuid", this.uuid);
        map.put("x", this.x);
        map.put("y", this.y);
        map.put("z", this.z);
        map.put("yaw", this.yaw);
        map.put("pitch", this.pitch);
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
            weakLoc = new WeakReference<Location>(new Location(world, x, y, z, yaw, pitch));
        }
        return weakLoc.get();
    }
}
