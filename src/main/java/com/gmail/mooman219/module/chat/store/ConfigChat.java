package com.gmail.mooman219.module.chat.store;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.ModuleType;

public class ConfigChat extends ConfigJackson {
    private static ConfigData data;

    public ConfigChat() {
        super(ModuleType.CHAT.getCast(), ModuleType.CHAT.getDirectory(), "chat", "config");
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
        public int radius = 2500; // Blocks^2 (50 blocks = 2500 blocks^2)
        public int globalDelay = 10000; // Milliseconds

        protected ConfigData() {}
    }
}
