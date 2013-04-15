package com.gmail.mooman219.module.region.store;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Chunk;
import com.gmail.mooman219.bukkit.DefaultTag;

@DefaultTag(key = "region")
public class CTRegion implements Serializable {
    private final Chunk head;
    private UUID parentKey;
    private transient RegionInformation parentInformation;
    
    private CTRegion(Chunk head, RegionInformation parentInformation) {
        this.head = head;
        setParentInformation(parentInformation);
    }
    
    public RegionInformation getParentInformation() {
        if(parentInformation == null) {
            WTRegion worldRegion = WTRegion.getWorldRegion(head.getWorld());
            RegionInformation information = worldRegion.getRegionInformation(parentKey);
            if(information == null) {
                information = worldRegion.getGlobalInformation();
            }
            setParentInformation(information);
        }
        return parentInformation;
    }
    
    public void setParentInformation(RegionInformation information) {
        this.parentKey = information.getKey();
        this.parentInformation = information;
    }
    
    public static CTRegion getChunkRegion(Chunk chunk) {
        CTRegion region = chunk.getTag().get(CTRegion.class);
        if(region == null) {
            WTRegion worldRegion = WTRegion.getWorldRegion(chunk.getWorld());  
            region = new CTRegion(chunk, worldRegion.getGlobalInformation());
            chunk.getTag().set(region);
        }
        return region;
    }
}
