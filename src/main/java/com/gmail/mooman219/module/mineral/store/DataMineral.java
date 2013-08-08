package com.gmail.mooman219.module.mineral.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.frame.serialize.jack.FastLocation;
import com.gmail.mooman219.layout.ModuleType;

public class DataMineral extends ConfigJackson {
    private static ConfigData data;

    public DataMineral() {
        super(ModuleType.MINERAL.getCast(), ModuleType.MINERAL.getDirectory(), "minerals", "data");
    }

    public static ConfigData getData() {
        return data;
    }

    public static HashMap<FastLocation, FastMineral> getMinerals() {
        return data.mineralMap;
    }

    @Override
    public void onLoad(File file) {
        data = JsonHelper.fromJackson(file, ConfigData.class);
        data.mineralMap.clear();
        for(FastMineral mineral : data.minerals) {
            data.mineralMap.put(mineral.getLocation(), mineral);
        }
    }

    @Override
    public void onSave(File file) {
        data.minerals.clear();
        for(FastMineral mineral : data.mineralMap.values()) {
            data.minerals.add(mineral);
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
            data = new ConfigData();
        }
    }

    public static class ConfigData {
        public transient HashMap<FastLocation, FastMineral> mineralMap = new HashMap<FastLocation, FastMineral>();
        public ArrayList<FastMineral> minerals = new ArrayList<FastMineral>();

        protected ConfigData() {}
    }
}
