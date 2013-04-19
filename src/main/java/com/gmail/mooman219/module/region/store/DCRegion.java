package com.gmail.mooman219.module.region.store;

import java.io.Serializable;
import java.util.UUID;

import org.bukkit.Chunk;

public class DCRegion implements Serializable {
    private final Chunk head;
    private String parentUUID;
    private transient CSRegionInformation parentInformation;

    private DCRegion(Chunk head, CSRegionInformation parentInformation) {
        this.head = head;
        setParentInformation(parentInformation);
    }

    public CSRegionInformation getParentInformation() {
        if(parentInformation == null) {
            WTRegion worldRegion = WTRegion.getWorldRegion(head.getWorld());
            RegionInformation information = worldRegion.getRegionInformation(parentUUID);
            if(information == null) {
                information = worldRegion.getGlobalInformation();
            }
            setParentInformation(information);
        }
        return parentInformation;
    }

    public void setParentInformation(CSRegionInformation information) {
        this.parentUUID = information.uuid;
        this.parentInformation = information;
    }
}
