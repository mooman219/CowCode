package com.gmail.mooman219.module.mineral.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.handler.task.event.TickSecondSyncEvent;
import com.gmail.mooman219.module.mineral.MineralManager;

public class ListenerTime implements Listener{
    @EventHandler()
    public void onSecond(TickSecondSyncEvent event) {
        MineralManager.tick();
    }
}
