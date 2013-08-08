package com.gmail.mooman219.module.region;

import java.util.UUID;

import org.bukkit.Location;

import com.gmail.mooman219.frame.serialize.jack.FastChunkLocation;
import com.gmail.mooman219.module.region.store.FastRegion;
import com.gmail.mooman219.module.region.store.DataChunk;
import com.gmail.mooman219.module.region.store.DataRegion;

public class RegionManager {
    /**
     * Returns the newly created region.
     * Otherwise returns null if a region matching the id already exists.
     */
    public static FastRegion addRegion(String id, String name) {
        FastRegion information = getRegion(id);
        if(information == null) {
            information = new FastRegion(id, name);
            DataRegion.getRegions().put(information.getUUID(), information);
            return information;
        } else {
            return null;
        }
    }

    public static void setRegion(Location location, FastRegion region) {
        FastChunkLocation chunk = new FastChunkLocation(location.getChunk());
        if(region.equals(DataRegion.getGlobal())) {
            DataChunk.getChunks().remove(chunk);
        } else {
            DataChunk.getChunks().put(chunk, region.getUUID());
        }
    }

    /**
     * Returns the region located at the given location.
     * Otherwise returns the global region.
     */
    public static FastRegion getRegion(Location location) {
        FastRegion region = RegionManager.getRegion(DataChunk.getChunks().get(new FastChunkLocation(location.getChunk())));
        return region != null ? region : getGlobal();
    }

    /**
     * Returns the region with the matching uuid.
     * Otherwise returns null.
     */
    public static FastRegion getRegion(UUID uuid) {
        if(uuid == null) {
            return DataRegion.getGlobal();
        }
        FastRegion info = DataRegion.getRegions().get(uuid);
        return info != null ? info : DataRegion.getGlobal();
    }

    /**
     * Returns the region with the matching id.
     * Otherwise returns null.
     */
    public static FastRegion getRegion(String id) {
        if(id.equalsIgnoreCase("global")) {
            return DataRegion.getGlobal();
        }
        for(FastRegion info : DataRegion.getRegions().values()) {
            if(info.getID().equals(id)) {
                return info;
            }
        }
        return null;
    }

    public static FastRegion getGlobal() {
        return DataRegion.getGlobal();
    }
}
