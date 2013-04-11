package com.gmail.mooman219.handler.config;

import com.gmail.mooman219.core.Handler;
import com.gmail.mooman219.core.Loader;

public class CHConfig implements Handler {
    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][H][Database] ";

    public static ConfigGlobal configGlobal;

    public void onEnable() {
        configGlobal = new ConfigGlobal();
        Loader.info(cast + "Loaded " + configGlobal.fileName);

        Loader.info(cast + "Enabled");
    }

    public void onDisable() {        
        Loader.info(cast + "Disabled");
    }
}
