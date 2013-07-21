package com.gmail.mooman219.frame.serialize.json;

import java.lang.ref.WeakReference;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JsonData;
import com.google.gson.annotations.SerializedName;

public class BasicChunkLocation implements JsonData {
    @SerializedName("World") private String world;
    @SerializedName("UUID") private UUID uuid;
    @SerializedName("ChunkPos") private BasicVectorInteger vector;

    private transient WeakReference<Chunk> weakChunk;

    public BasicChunkLocation(Chunk chunk) {
        this.world = chunk.getWorld().getName();
        this.uuid = chunk.getWorld().getUID();
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
            this.uuid = world.getUID();
            this.world = world.getName();
            Vector vec = vector.toVector();
            weakChunk = new WeakReference<Chunk>(world.getChunkAt(vec.getBlockX(), vec.getBlockZ()));
        }
        return weakChunk.get();
    }

    /**
     * @return True if there is a similar position.
     */
    public boolean match(Location location) {
        return (vector.getX() == location.getChunk().getX() && vector.getZ() == location.getChunk().getZ()) && (location.getWorld().getName().equals(world) || location.getWorld().getUID().equals(uuid));
    }

    public BasicVectorInteger getVector() {
        return vector;
    }

    public UUID getUUID() {
        return uuid;
    }

    public String getWorld() {
        return world;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicChunkLocation fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicChunkLocation.class);
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
        BasicChunkLocation other = (BasicChunkLocation) obj;
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
