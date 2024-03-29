package com.gmail.mooman219.module.service.api;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.bull.CDPlayer;

/*
 * Called in AsyncPlayerPreLoginEvent
 */
public class DataVerifyEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer player;
    private final AsyncPlayerPreLoginEvent event;

    public DataVerifyEvent(AsyncPlayerPreLoginEvent event, CDPlayer player) {
        super(true);
        this.player = player;
        this.event = event;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CDPlayer getPlayer() {
        return player;
    }

    public AsyncPlayerPreLoginEvent getEvent() {
        return event;
    }
}
