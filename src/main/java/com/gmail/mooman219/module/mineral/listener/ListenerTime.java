package com.gmail.mooman219.module.mineral.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.frame.event.TickSecondAsyncEvent;
import com.gmail.mooman219.module.mineral.MineralManager;

public class ListenerTime implements Listener{
    @EventHandler()
    public void onSecond(TickSecondAsyncEvent event){
        MineralManager.update();
    }
}
