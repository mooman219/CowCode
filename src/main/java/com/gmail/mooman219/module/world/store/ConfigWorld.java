package com.gmail.mooman219.module.world.store;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.ModuleType;

public class ConfigWorld extends ConfigJackson {
    private static ConfigData data;

    public ConfigWorld() {
        super(ModuleType.WORLD.getCast(), ModuleType.WORLD.getDirectory(), "world", "config");
    }

    public static ConfigData getData() {
        return data;
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
        public boolean disableBlockBurn = true;
        public boolean disableBlockSpread = true;
        public boolean disableBlockFade = true;
        public boolean disableBlockForm = true;
        public boolean disableBlockGrow = true;
        public boolean disableBlockFromTo = true;
        public boolean disableLeafDecay = true;
        public boolean disableLightningStrike = true;
        public boolean disableStructureGrow = true;
        public boolean disableWorldSaving = false;
        public boolean disableBuilding = false;

        protected ConfigData() {}
    }
}
