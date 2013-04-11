package com.gmail.mooman219.frame.event;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.gmail.mooman219.module.service.player.PlayerData;

public class CCEventFactory {
    public static DataCreateEvent callDataCreateEvent(PlayerLoginEvent event, PlayerData playerData) {
        DataCreateEvent ccEvent = new DataCreateEvent(event, playerData);
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }
    
    public static DataRemovalEvent callDataRemovalEvent(boolean async, PlayerData playerData) {
        DataRemovalEvent ccEvent = new DataRemovalEvent(async, playerData);
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }
    
    public static DataVerifyEvent callDataVerifyEvent(AsyncPlayerPreLoginEvent event, PlayerData playerData) {
        DataVerifyEvent ccEvent = new DataVerifyEvent(event, playerData);
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }
    
    public static TickSecondAsyncEvent callTickSecondAsyncEvent() {
        TickSecondAsyncEvent ccEvent = new TickSecondAsyncEvent();
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }
    
    public static TickSecondSyncEvent callTickSecondSyncEvent() {
        TickSecondSyncEvent ccEvent = new TickSecondSyncEvent();
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }
}
