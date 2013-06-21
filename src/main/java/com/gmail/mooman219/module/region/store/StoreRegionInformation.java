package com.gmail.mooman219.module.region.store;
import java.util.ArrayList;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigBase;
import com.gmail.mooman219.module.region.CCRegion;

public class StoreRegionInformation extends ConfigBase {
    public static final CSRegionInformation globalInformation = new CSRegionInformation("global", "Global");
    public static HashMap<String, CSRegionInformation> regions = new HashMap<String, CSRegionInformation>();

    public StoreRegionInformation() {
        super(CCRegion.directory, "regions.yml");
        super.init();
        globalInformation.setDescription("No region exists here");
        globalInformation.setCombatType(RegionCombatType.SAFE);
    }

    @Override
    public void onLoad() {
        regions = new HashMap<String, CSRegionInformation>();
        for(CSRegionInformation regionInformation : loadVar("Regions", new ArrayList<CSRegionInformation>())) {
            regions.put(regionInformation.getUUID(), regionInformation);
        }
    }

    @Override
    public void onSave() {
        ArrayList<CSRegionInformation> regions = new ArrayList<CSRegionInformation>();
        regions.addAll(StoreRegionInformation.regions.values());
        saveVar("Regions", regions);
    }

    public static CSRegionInformation addInformation(String id, String name) {
        CSRegionInformation information = getInformationByID(id);
        if(getInformationByID(id) == null) {
            information = new CSRegionInformation(id, name);
            regions.put(information.getUUID(), information);
        }
        return information;
    }

    public static CSRegionInformation getInformationByID(String id) {
        if(id.equals("global")) {
            return globalInformation;
        }
        for(CSRegionInformation information : regions.values()) {
            if(information.getID().equals(id)) {
                return information;
            }
        }
        return null;
    }

    public static CSRegionInformation getInformation(String uuid) {
        if(regions.containsKey(uuid)) {
            return regions.get(uuid);
        }
        return globalInformation;
    }
}