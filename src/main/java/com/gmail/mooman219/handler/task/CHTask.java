package com.gmail.mooman219.handler.task;

import com.gmail.mooman219.core.CowHandler;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.task.task.TimeKeeperAsync;
import com.gmail.mooman219.handler.task.task.TimeKeeperSync;

public class CHTask implements CowHandler {
    public final Loader plugin;

    public final static String cast = "[CC][H][Task] ";

    public static TaskManager manager;

    public CHTask(Loader plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        Loader.info(cast + "Starting TaskManager");
        manager = new TaskManager();
        manager.start(plugin);
        Loader.info(cast + "Starting TimeKeepers");
        manager.runThread(new TimeKeeperAsync(), false, 1000, 1000).setName("CC AsyncTimeKeeper");
        manager.runTaskTimer(new TimeKeeperSync(), 20, 20);
        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Stopping TaskManager");
        manager.stop();
        Loader.info(cast + "Disabled");
    }
}
