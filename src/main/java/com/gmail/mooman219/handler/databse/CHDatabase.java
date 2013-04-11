package com.gmail.mooman219.handler.databse;

import com.gmail.mooman219.core.Handler;
import com.gmail.mooman219.core.Loader;

public class CHDatabase implements Handler {
    public final static String cast = "[CC][H][Database] ";

    public static DatabaseManager manager;

    public void onEnable() {
        Loader.info(cast + "Starting DatabaseManager");
        manager = new DatabaseManager();
        manager.start();

        Loader.info(cast + "Enabled");
    }

    public void onDisable() {
        Loader.info(cast + "Stopping DatabaseManager");
        manager.stop();
        manager = null;
        
        Loader.info(cast + "Disabled");
    }
}
