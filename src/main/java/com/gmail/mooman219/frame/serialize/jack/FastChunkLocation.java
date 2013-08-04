package com.gmail.mooman219.frame.serialize.jack;

import java.lang.ref.WeakReference;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JacksonData;

public class FastChunkLocation implements JacksonData {
    private static final long serialVersionUID = 1192644931243147486L;
    private String world;
    private UUID uuid;
    private int x;
    private int z;

    private transient WeakReference<Chunk> weakChunk;

    protected FastChunkLocation() {}

    public FastChunkLocation(Chunk chunk) {
        this.world = chunk.getWorld().getName();
        this.uuid = chunk.getWorld().getUID();
        this.x = chunk.getX();
        this.z = chunk.getZ();
    }

    public String getWorld() {
        return world;
    }

    public UUID getUUID() {
        return uuid;
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
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
            weakChunk = new WeakReference<Chunk>(world.getChunkAt(x, z));
        }
        return weakChunk.get();
    }

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastChunkLocation deserialize(String data) {
        return JsonHelper.fromJackson(data, FastChunkLocation.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((world == null) ? 0 : world.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + x;
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
        FastChunkLocation other = (FastChunkLocation) obj;
        if (world == null) {
            if (other.world != null) {
                return false;
            }
        } else if (!world.equals(other.world)) {
            return false;
        }
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        if (x != other.x) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }
}
