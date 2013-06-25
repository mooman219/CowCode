package com.gmail.mooman219.frame.serialize.json;

import java.lang.ref.WeakReference;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class BasicRichLocation {
    @SerializedName("World") private String world;
    @SerializedName("UUID") private String uuid;
    @SerializedName("Yaw") private float yaw;
    @SerializedName("Pitch") private float pitch;
    @SerializedName("Pos") private BasicVectorDouble vector;

    private transient WeakReference<Location> weakLoc;

    public BasicRichLocation(Location loc) {
        this.world = loc.getWorld().getName();
        this.uuid = loc.getWorld().getUID().toString();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
        this.vector = new BasicVectorDouble(loc.toVector());
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
            weakLoc = new WeakReference<Location>(new Location(world, vec.getX(), vec.getY(), vec.getZ(), yaw, pitch));
        }
        return weakLoc.get();
    }

    @Override
    public String toString() {
        return BasicRichLocation.getGson().toJson(this);
    }

    public static BasicRichLocation fromString(String string) {
        return BasicRichLocation.getGson().fromJson(string, BasicRichLocation.class);
    }

    public static Gson getGson() {
        return new GsonBuilder()
        .registerTypeAdapter(BasicVectorDouble.class, BasicVectorDouble.getAdapter())
        .create();
    }
}
