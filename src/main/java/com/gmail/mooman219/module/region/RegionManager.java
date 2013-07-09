package com.gmail.mooman219.module.region;

import java.util.UUID;

import org.bukkit.Location;

import com.gmail.mooman219.frame.serialize.json.BasicChunkLocation;
import com.gmail.mooman219.module.region.store.BasicRegion;
import com.gmail.mooman219.module.region.store.StoreChunk;
import com.gmail.mooman219.module.region.store.StoreRegion;

public class RegionManager {
    public static BasicRegion addRegion(String id, String name) {
        BasicRegion information = gerRegion(id);
        if(gerRegion(id) == null) {
            information = new BasicRegion(id, name);
            StoreRegion.getRegions().put(information.getUUID(), information);
        }
        return information;
    }

    public static void setRegion(Location location, BasicRegion region) {
        BasicChunkLocation chunk = new BasicChunkLocation(location.getChunk());
        if(region.equals(StoreRegion.getGlobalInfo())) {
            StoreChunk.getChunks().remove(chunk);
        } else {
            StoreChunk.getChunks().put(chunk, region.getUUID());
        }
    }

    public static BasicRegion getRegion(Location location) {
        BasicChunkLocation chunk = new BasicChunkLocation(location.getChunk());
        UUID regionUID = StoreChunk.getChunks().get(chunk);
        if(regionUID == null) {
            return StoreRegion.getGlobalInfo();
        } else {
            BasicRegion region = RegionManager.getRegion(regionUID);
            if(region.equals(StoreRegion.getGlobalInfo())) {
                StoreChunk.getChunks().remove(chunk);
            }
            return region;
        }
    }

    public static BasicRegion getRegion(UUID uuid) {
        BasicRegion info = StoreRegion.getRegions().get(uuid);
        return info != null ? info : StoreRegion.getGlobalInfo();
    }

    public static BasicRegion gerRegion(String id) {
        if(id.equalsIgnoreCase("global")) {
            return StoreRegion.getGlobalInfo();
        }
        for(BasicRegion info : StoreRegion.getRegions().values()) {
            if(info.getID().equals(id)) {
                return info;
            }
        }
        return null;
    }
}
