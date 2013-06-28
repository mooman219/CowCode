package com.gmail.mooman219.module.region.store;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.Gson;

public class StoreRegionInfo extends ConfigJson {
    public static transient final BasicRegionInfo globalInfo = new BasicRegionInfo("GLOBALREGIONUUID-90f5e0f50661c3951a2", "global", "Global")
                                                      .setDescription("No region exists here")
                                                      .setCombatType(RegionCombatType.SAFE);
    public static transient HashMap<String, BasicRegionInfo> regions = new HashMap<String, BasicRegionInfo>();
    private static ArrayList<BasicRegionInfo> saveable_regions = new ArrayList<BasicRegionInfo>();
    
    public StoreRegionInfo(String directory) {
        super(directory, "regions", "yml");
    }

    @Override
    public void onSave(FileWriter writer) {
        saveable_regions.clear();
        saveable_regions.addAll(StoreRegionInfo.regions.values());
        getGson().toJson(this, writer);
    }

    @Override
    public void onLoad(FileReader reader) {
        getGson().fromJson(reader, StoreRegionInfo.class);
        regions = new HashMap<String, BasicRegionInfo>();
        for(BasicRegionInfo regionInfo : saveable_regions) {
            regions.put(regionInfo.getUUID(), regionInfo);
        }
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .setPrettyPrinting()
        .create();
    }

    public static BasicRegionInfo addInfo(String id, String name) {
        BasicRegionInfo information = getInfoByID(id);
        if(getInfoByID(id) == null) {
            information = new BasicRegionInfo(id, name);
            regions.put(information.getUUID(), information);
        }
        return information;
    }

    public static BasicRegionInfo getInfoByID(String id) {
        if(id.equals("global")) {
            return globalInfo;
        }
        for(BasicRegionInfo info : regions.values()) {
            if(info.getID().equals(id)) {
                return info;
            }
        }
        return null;
    }

    public static BasicRegionInfo getInfo(String uuid) {
        BasicRegionInfo info = regions.get(uuid);
        return info != null ? info : globalInfo;
    }
}