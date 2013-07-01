package com.gmail.mooman219.frame.serialize.json;

import java.lang.ref.WeakReference;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.annotations.SerializedName;

public class BasicLocation {
    @SerializedName("World") private String world;
    @SerializedName("UUID") private String uuid;
    @SerializedName("Pos") private BasicVectorInteger vector;

    private transient WeakReference<Location> weakLoc;

    public BasicLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.uuid = loc.getWorld().getUID().toString();
        this.vector = new BasicVectorInteger(loc.toVector());
    }

    public Location toLocation() {
        if (weakLoc == null || weakLoc.get() == null) {
            World world = Bukkit.getWorld(this.uuid);
            if (world == null) {
                world = Bukkit.getWorld(this.world);
                if (world == null) {
                    throw new IllegalStateException("Cannot find world by UUID or name");
                }
            }
            Vector vec = vector.toVector();
            weakLoc = new WeakReference<Location>(new Location(world, vec.getX(), vec.getY(), vec.getZ()));
        }
        return weakLoc.get();
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicLocation fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicLocation.class);
    }

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((vector == null) ? 0 : vector.hashCode());
        result = prime * result + ((world == null) ? 0 : world.hashCode());
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
        BasicLocation other = (BasicLocation) obj;
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        if (vector == null) {
            if (other.vector != null) {
                return false;
            }
        } else if (!vector.equals(other.vector)) {
            return false;
        }
        if (world == null) {
            if (other.world != null) {
                return false;
            }
        } else if (!world.equals(other.world)) {
            return false;
        }
        return true;
    }
}
