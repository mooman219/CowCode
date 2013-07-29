package com.gmail.mooman219.handler.config;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.layout.HandlerType;

public class CHConfig extends CowHandler {
    private final static HandlerType type = HandlerType.CONFIG;

    private static ConfigGlobal configGlobal;

    public CHConfig(Loader plugin) {
        super(plugin);
    }

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
        configGlobal = new ConfigGlobal(getCast(), getDirectory());
    }

    public static ConfigGlobal getConfig() {
        return configGlobal;
    }
}
