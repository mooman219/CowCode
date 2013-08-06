package com.gmail.mooman219.handler.mysql;

import com.gmail.mooman219.handler.mysql.store.StoreMysql;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.layout.HandlerType;

public class CHMysql extends CowHandler {
    private static final HandlerType type = HandlerType.MYSQL;
    private static Manager manager;

    public StoreMysql storeMysql;

    @Override
    public HandlerType getType() {
        return type;
    }

    public static String getName() {
        return type.getName();
    }

    public static String getCast() {
        return type.getCast();
    }

    public static String getDirectory() {
        return type.getDirectory();
    }

    @Override
    public void onEnable() {
        storeMysql = new StoreMysql();
    }

    @Override
    public void onDisable() {
    }

    public static Manager getManager() {
        return manager;
    }

    public class Manager {
    }
}
