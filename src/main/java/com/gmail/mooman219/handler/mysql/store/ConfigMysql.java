package com.gmail.mooman219.handler.mysql.store;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.HandlerType;

public class ConfigMysql extends ConfigJackson {
    private static ConfigData data;

    public ConfigMysql() {
        super(HandlerType.MYSQL.getCast(), HandlerType.MYSQL.getDirectory(), "mysql", "config");
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
        public String serverAddress = "localhost";
        public int serverPort = 3306;
        public String databaseName = "unavowed";
        public String userName = "admin";
        public String userPassword = "j2TmYw9xTrpKXj7d37";
        public String tablePrefix = "unavowed_";

        protected ConfigData() {}
    }
}
