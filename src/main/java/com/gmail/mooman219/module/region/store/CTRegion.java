package com.gmail.mooman219.module.region.store;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Chunk;

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
}
