package com.gmail.mooman219.handler.mysql.store;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.layout.HandlerType;

public class StoreMysql extends ConfigJackson {
    public MysqlConfigData data;

    public StoreMysql() {
        super(HandlerType.MYSQL.getCast(), HandlerType.MYSQL.getDirectory(), "Mysql", "yml");
        data = new MysqlConfigData();
    }

    @Override
    public void onLoad(File file) {
        data = JsonHelper.fromJackson(file, MysqlConfigData.class);
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

    public static class MysqlConfigData {
        public String serverAddress = "localhost";
        public int serverPort = 3306;
        public String databaseName = "unavowed";
        public String userName = "admin";
        public String userPassword = "j2TmYw9xTrpKXj7d37";
        public String tablePrefix = "unavowed_";

        public MysqlConfigData() {}
    }
}
