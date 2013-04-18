package com.gmail.mooman219.frame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.module.CDPlayer;

public class DataVerifyEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer playerData;
    private final AsyncPlayerPreLoginEvent event;

    public DataVerifyEvent(AsyncPlayerPreLoginEvent event, CDPlayer playerData) {
        super(true);
        this.playerData = playerData;
        this.event = event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public CDPlayer getPlayerData() {
        return playerData;
    }

    public AsyncPlayerPreLoginEvent getEvent() {
        return event;
    }
}
