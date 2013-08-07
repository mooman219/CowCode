package com.gmail.mooman219.module.region.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.region.type.RegionCombatType;

public class StoreRegion extends ConfigJackson {
    private static RegionConfigData data;

    public StoreRegion() {
        super(ModuleType.REGION.getCast(), ModuleType.REGION.getDirectory(), "region", "data");
    }

    public static RegionConfigData getData() {
        return data;
    }

    public static FastRegion getGlobal() {
        return data.globalInfo;
    }

    public static HashMap<UUID, FastRegion> getRegions() {
        return data.regionMap;
    }

    @Override
    public void onLoad(File file) {
        data = JsonHelper.fromJackson(file, RegionConfigData.class);
        data.regionMap.clear();
        for(FastRegion region : data.regions) {
            data.regionMap.put(region.getUUID(), region);
        }
    }

    @Override
    public void onSave(File file) {
        data.regions.clear();
        for(FastRegion region : data.regionMap.values()) {
            data.regions.add(region);
        }
        try {
            JsonHelper.getFancyJackson().writeValue(file, data);
        } catch(JsonGenerationException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateData() {
        if(data == null) {
            data = new RegionConfigData();
        }
    }

    public static class RegionConfigData {
        public transient final FastRegion globalInfo;
        public transient HashMap<UUID, FastRegion> regionMap = new HashMap<UUID, FastRegion>();
        public ArrayList<FastRegion> regions = new ArrayList<FastRegion>();

        protected RegionConfigData() {
            globalInfo = new FastRegion("283453ad-094b-92b7-b191-a07bff41d667", "global", "Global");
            globalInfo.setDescription("No region exists here");
            globalInfo.setCombatType(RegionCombatType.SAFE);
        }
    }
}
