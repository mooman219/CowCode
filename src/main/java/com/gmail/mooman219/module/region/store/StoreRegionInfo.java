package com.gmail.mooman219.module.region.store;
import java.util.ArrayList;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigYaml;
import com.gmail.mooman219.module.region.CCRegion;

public class StoreRegionInfo extends ConfigYaml {
    public static final CSRegionInfo globalInfo = new CSRegionInfo("GLOBALREGIONUUID-90f5e0f50661c3951a2", "global", "Global")
                                                      .setDescription("No region exists here")
                                                      .setCombatType(RegionCombatType.SAFE);
    public static HashMap<String, CSRegionInfo> regions = new HashMap<String, CSRegionInfo>();

    public StoreRegionInfo() {
        super(CCRegion.directory, "regions.yml");
        globalInfo.setDescription("No region exists here");
        globalInfo.setCombatType(RegionCombatType.SAFE);
    }

    @Override
    public void onLoad() {
        regions = new HashMap<String, CSRegionInfo>();
        for(CSRegionInfo regionInfo : loadVar("Regions", new ArrayList<CSRegionInfo>())) {
            regions.put(regionInfo.getUUID(), regionInfo);
        }
    }

    @Override
    public void onSave() {
        ArrayList<CSRegionInfo> regions = new ArrayList<CSRegionInfo>();
        regions.addAll(StoreRegionInfo.regions.values());
        saveVar("Regions", regions);
    }

    public static CSRegionInfo addInfo(String id, String name) {
        CSRegionInfo information = getInfoByID(id);
        if(getInfoByID(id) == null) {
            information = new CSRegionInfo(id, name);
            regions.put(information.getUUID(), information);
        }
        return information;
    }

    public static CSRegionInfo getInfoByID(String id) {
        if(id.equals("global")) {
            return globalInfo;
        }
        for(CSRegionInfo info : regions.values()) {
            if(info.getID().equals(id)) {
                return info;
            }
        }
        return null;
    }

    public static CSRegionInfo getInfo(String uuid) {
        CSRegionInfo info = regions.get(uuid);
        return info != null ? info : globalInfo;
    }
}