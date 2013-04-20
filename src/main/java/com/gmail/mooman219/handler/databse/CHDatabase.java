package com.gmail.mooman219.handler.databse;

import com.gmail.mooman219.core.CowHandler;
import com.gmail.mooman219.core.Loader;

public class CHDatabase implements CowHandler {
    public final static String cast = "[CC][H][Database] ";

    public static DatabaseManager manager;

    @Override
    public void onEnable() {
        Loader.info(cast + "Starting DatabaseManager");
        manager = new DatabaseManager();
        manager.start();

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Stopping DatabaseManager");
        manager.stop();
        manager = null;

        Loader.info(cast + "Disabled");
    }
}
