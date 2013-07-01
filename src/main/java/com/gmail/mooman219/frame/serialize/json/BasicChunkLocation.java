package com.gmail.mooman219.frame.serialize.json;

import java.lang.ref.WeakReference;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.annotations.SerializedName;

public class BasicChunkLocation {
    @SerializedName("World") private String world;
    @SerializedName("UUID") private String uuid;
    @SerializedName("Chunk_Pos") private BasicVectorInteger vector;

    private transient WeakReference<Chunk> weakChunk;

    public BasicChunkLocation(Chunk chunk) {
        this.world = chunk.getWorld().getName();
        this.uuid = chunk.getWorld().getUID().toString();
        this.vector = new BasicVectorInteger(chunk.getX(), 0, chunk.getZ());
    }

    public Chunk toChunk() {
        if (weakChunk == null || weakChunk.get() == null) {
            World world = Bukkit.getWorld(this.uuid);
            if (world == null) {
                world = Bukkit.getWorld(this.world);
                if (world == null) {
                    throw new IllegalStateException("Cannot find world by UUID or name");
                }
            }
            Vector vec = vector.toVector();
            weakChunk = new WeakReference<Chunk>(world.getChunkAt(vec.getBlockX(), vec.getBlockZ()));
        }
        return weakChunk.get();
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicChunkLocation fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicChunkLocation.class);
    }
}
