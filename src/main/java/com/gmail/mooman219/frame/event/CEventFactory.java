package com.gmail.mooman219.frame.event;

import org.bukkit.Bukkit;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import com.gmail.mooman219.bull.CDPlayer;

public class CEventFactory {
    public static DataCreateEvent callDataCreateEvent(PlayerLoginEvent event, CDPlayer player) {
        DataCreateEvent ccEvent = new DataCreateEvent(event, player);
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }

    public static DataRemovalEvent callDataRemovalEvent(boolean async, CDPlayer player) {
        DataRemovalEvent ccEvent = new DataRemovalEvent(async, player);
        Bukkit.getPluginManager().callEvent(ccEvent);
        return ccEvent;
    }

    public static DataVerifyEvent callDataVerifyEvent(AsyncPlayerPreLoginEvent event, CDPlayer player) {
        DataVerifyEvent ccEvent = new DataVerifyEvent(event, player);
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
