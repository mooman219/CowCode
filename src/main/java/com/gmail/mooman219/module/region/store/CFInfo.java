package com.gmail.mooman219.module.region.store;

import java.util.ArrayList;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigBase;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.RegionCombatType;

public class CFInfo extends ConfigBase {
    public static final CSRegionInformation global = new CSRegionInformation(RegionCombatType.SAFE, "global", "Global", "No region exists here");
    public static HashMap<String, CSRegionInformation> regions = new HashMap<String, CSRegionInformation>();

    public CFInfo() {
        super(CCRegion.directory, "region.information.yml");
        super.init();
    }

    @Override
    public void onLoad() {
        regions = new HashMap<String, CSRegionInformation>();
        for(CSRegionInformation regionInformation : loadVar("Regions", new ArrayList<CSRegionInformation>())) {
            regions.put(regionInformation.uuid, regionInformation);
        }
        if(regions.size() == 0) {
            regions.put(global.uuid, global);
        }
    }

    @Override
    public void onSave() {
        ArrayList<CSRegionInformation> regions = new ArrayList<CSRegionInformation>();
        regions.addAll(CFInfo.regions.values());
        saveVar("Regions", regions);
    }
    
    public static void addInformation(String id, String name) {
        if(getInformationByID(id) == null) {
            CSRegionInformation newInformation = new CSRegionInformation(id, name);
            regions.put(newInformation.uuid, newInformation);
        }
    }
    
    public static CSRegionInformation getInformationByID(String id) {
        for(CSRegionInformation regionInformation : regions.values()) {
            if(regionInformation.id.equalsIgnoreCase(id)) {
                return regionInformation;
            }
        }
        return null;
    }
    
    public static CSRegionInformation getInformation(String uuid) {
        if(regions.containsKey(uuid)) {
            return regions.get(uuid);
        } else {
            return global;
        }
    }
}
