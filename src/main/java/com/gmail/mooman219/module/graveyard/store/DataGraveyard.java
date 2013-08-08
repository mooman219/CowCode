package com.gmail.mooman219.module.graveyard.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.ModuleType;

public class DataGraveyard extends ConfigJackson {
    private static ConfigData data;

    public DataGraveyard() {
        super(ModuleType.GRAVEYARD.getCast(), ModuleType.GRAVEYARD.getDirectory(), "graveyards", "data");
    }

    public static ConfigData getData() {
        return data;
    }

    public static ArrayList<FastGraveyard> getGraveyards() {
        return data.graveyards;
    }

    @Override
    public void onLoad(File file) {
        data = JsonHelper.fromJackson(file, ConfigData.class);
    }

    @Override
    public void onSave(File file) {
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
        public ArrayList<FastGraveyard> graveyards = new ArrayList<FastGraveyard>();

        protected ConfigData() {}
    }
}
