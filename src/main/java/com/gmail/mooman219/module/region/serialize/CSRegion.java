package com.gmail.mooman219.module.region.serialize;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Chunk;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.gmail.mooman219.frame.serialize.CSChunkLocation;
import com.gmail.mooman219.module.region.store.CFInfo;

@SerializableAs(value = "CSRegion")
public class CSRegion implements ConfigurationSerializable {
    public final CSChunkLocation chunk;
    //
    private String parentUUID;
    private transient SoftReference<CSRegionInformation> softRegionInformation;

    public CSRegion(String parentUUID, Chunk chunk) {
        this.parentUUID = parentUUID;
        this.chunk = new CSChunkLocation(chunk);
    }

    public CSRegion(Map<String, Object> map) {
        this.parentUUID = (String) map.get("parentuuid");
        this.chunk = (CSChunkLocation) map.get("chunk");
    }

    public static CSRegion deserialize(Map<String, Object> map) {
        return new CSRegion(map);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("chunk", chunk);
        map.put("parentuuid", parentUUID);
        return map;
    }

    public final Chunk getChunk() {
        return chunk.getChunk();
    }

    public final String getParentUUIF() {
        return parentUUID;
    }

    public final CSRegionInformation getCSRegionInformation() {
        if (softRegionInformation == null || softRegionInformation.get() == null) {
            softRegionInformation = new SoftReference<CSRegionInformation>(CFInfo.getInformation(parentUUID));
        }
        return softRegionInformation.get();
    }

    public final void setCSRegionInformation(CSRegionInformation regionInformation) {
        this.parentUUID = regionInformation.uuid;
        if(softRegionInformation != null) {
            softRegionInformation.clear();
        }
        softRegionInformation = new SoftReference<CSRegionInformation>(regionInformation);
    }
}
