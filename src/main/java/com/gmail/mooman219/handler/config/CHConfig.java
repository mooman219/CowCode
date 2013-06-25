package com.gmail.mooman219.handler.config;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.layout.CowHandler;

public class CHConfig implements CowHandler {
    public final static String cast = "[CC][Config] ";
    public final static String directory = "plugins/CowCraft/";

    public static ConfigGlobal configGlobal;

    public CHConfig() {}

    @Override
    public String getName() {
        return "Config";
    }

    @Override
    public void onEnable() {
        configGlobal = new ConfigGlobal(directory);
        Loader.info(cast + "Loaded " + configGlobal.getFile().getName());
    }

    @Override
    public void onDisable() {}
}
