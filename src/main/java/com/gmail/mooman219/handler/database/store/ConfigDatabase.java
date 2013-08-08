package com.gmail.mooman219.handler.database.store;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.HandlerType;

public class ConfigDatabase extends ConfigJackson {
    private static ConfigData data;

    public ConfigDatabase() {
        super(HandlerType.DATABASE.getCast(), HandlerType.DATABASE.getDirectory(), "database", "config");
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
        public int downloadTimeout = 5; // Seconds
        public long connectionRetryDelay = 2000;
        public String hostname = "localhost";
        public int portnmbr = 27017;
        public String username = "cow";
        public String password = "c4qNflnf6zQWp9h2";

        protected ConfigData() {}
    }
}
