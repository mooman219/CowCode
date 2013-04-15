package com.gmail.mooman219.module.region.store;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.World;

import com.gmail.mooman219.bukkit.DefaultTag;

@DefaultTag(key = "region")
public class WTRegion implements Serializable {
    private final RegionInformation globalInformation = new RegionInformation().setName("Global").setDescription("This is the head region for the world.");
    private HashMap<UUID, RegionInformation> information = new HashMap<UUID, RegionInformation>();

    private WTRegion(){}
    
    public boolean containsRegionInformation(UUID key) {
        return information.containsKey(key);
    }

    public RegionInformation getRegionInformation(UUID key) {
        if(information.containsKey(key)) {
            return information.get(key);
        }
        return null;
    }
    
    public RegionInformation getGlobalInformation() {
        return globalInformation;
    }

    public static WTRegion getWorldRegion(World world) {
        WTRegion region = world.getTag().get(WTRegion.class);
        if(region == null) {
            region = new WTRegion();
            world.getTag().set(region);
        }
        return region;
    }
}