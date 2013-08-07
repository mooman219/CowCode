package com.gmail.mooman219.module.region.store;
import java.util.UUID;

import org.bukkit.Chunk;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.FastChunkLocation;
import com.gmail.mooman219.layout.JacksonData;

public class FastChunkRegion implements JacksonData {
    private static final long serialVersionUID = 5453346189721430905L;
    private FastChunkLocation chunk;
    private UUID uuid;

    protected FastChunkRegion() {};

    public FastChunkRegion(FastChunkLocation chunk, UUID uuid) {
        this.chunk = chunk;
        this.uuid = uuid;
    }

    public FastChunkLocation getChunk() {
        return chunk;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setChunk(FastChunkLocation chunk) {
        this.chunk = chunk;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public Chunk toChunk() {
        return chunk.toChunk();
    }

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastChunkRegion deserialize(String data) {
        return JsonHelper.fromJackson(data, FastChunkRegion.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((chunk == null) ? 0 : chunk.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
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
        FastChunkRegion other = (FastChunkRegion) obj;
        if (chunk == null) {
            if (other.chunk != null) {
                return false;
            }
        } else if (!chunk.equals(other.chunk)) {
            return false;
        }
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }
}