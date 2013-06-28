package com.gmail.mooman219.frame.serialize.yaml;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs(value = "CSChunkLocation")
public class CSChunkLocation implements ConfigurationSerializable{
    private final String world;
    private final String uuid;
    private final int cx;
    private final int cz;

    private transient WeakReference<Chunk> weakChunk;

    public CSChunkLocation(Location loc) {
        this(loc.getChunk());
    }

    public CSChunkLocation(Chunk chunk) {
        this.world = chunk.getWorld().getName();
        this.uuid = chunk.getWorld().getUID().toString();
        this.cx = chunk.getX();
        this.cz = chunk.getZ();
    }

    public CSChunkLocation(Map<String, Object> map) {
        this.world = (String) map.get("world");
        this.uuid = (String) map.get("uuid");
        this.cx = (Integer) map.get("cx");
        this.cz = (Integer) map.get("cz");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = CSChunkLocation.class.hashCode();
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        result = prime * result + ((world == null) ? 0 : world.hashCode());
        result = prime * result + cx;
        result = prime * result + cz;
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
        CSChunkLocation other = (CSChunkLocation) obj;
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
        if (cx != other.cx) {
            return false;
        }
        if (cz != other.cz) {
            return false;
        }
        return true;
    }

    public static CSChunkLocation deserialize(Map<String, Object> map) {
        return new CSChunkLocation(map);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("world", this.world);
        map.put("uuid", this.uuid);
        map.put("cx", this.cx);
        map.put("cz", this.cz);
        return map;
    }

    public final Chunk getChunk() {
        if (weakChunk == null || weakChunk.get() == null) {
            World world = Bukkit.getWorld(this.uuid);
            if (world == null) {
                world = Bukkit.getWorld(this.world);
                if (world == null) {
                    throw new IllegalStateException("Cannot find world by UUID or name");
                }
            }
            weakChunk = new WeakReference<Chunk>(world.getChunkAt(cx, cz));
        }
        return weakChunk.get();
    }
}
