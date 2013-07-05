package com.gmail.mooman219.module.region.store;
import java.util.UUID;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicChunkLocation;
import com.gmail.mooman219.layout.JsonData;

public class BasicChunkRegion implements JsonData {
    // This identifies the region, don't fuck with it
    private BasicChunkLocation chunk;
    private UUID uuid;

    public BasicChunkRegion(BasicChunkLocation chunk, UUID uuid) {
        this.chunk = chunk;
        this.uuid = uuid;
    }

    public BasicChunkLocation getChunk() {
        return chunk;
    }

    public void setChunk(BasicChunkLocation chunk) {
        this.chunk = chunk;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicChunkRegion fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicChunkRegion.class);
    }

    /**
     * Needed methods
     */

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
        BasicChunkRegion other = (BasicChunkRegion) obj;
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