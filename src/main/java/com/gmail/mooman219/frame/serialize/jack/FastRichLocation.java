package com.gmail.mooman219.frame.serialize.jack;

import java.lang.ref.WeakReference;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JacksonData;

public class FastRichLocation implements JacksonData {
    private static final long serialVersionUID = 5389868126093894172L;
    private String world;
    private UUID uuid;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    private transient WeakReference<Location> weakLocation;

    protected FastRichLocation() {}

    public FastRichLocation(Location location) {
        this.world = location.getWorld().getName();
        this.uuid = location.getWorld().getUID();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public float getYaw() {
        return yaw;
    }

    public float getPitch() {
        return pitch;
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
            weakLocation = new WeakReference<Location>(new Location(world, x, y, z, yaw, pitch));
        }
        return weakLocation.get();
    }

    public boolean isSimilar(Location location) {
        return x == location.getX() && y == location.getY() && z == location.getZ();
    }

    public boolean isSimilar(FastRichLocation location) {
        return x == location.getX() && y == location.getY() && z == location.getZ();
    }

    /**
     * Serialization and Deserialization
     */

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastRichLocation deserialize(String data) {
        return JsonHelper.fromJackson(data, FastRichLocation.class);
    }

    /**
     * HashCode and Equals
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        FastRichLocation other = (FastRichLocation) obj;
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
}
