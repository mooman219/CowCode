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

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicLocation fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicLocation.class);
    }
}
