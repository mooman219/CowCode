package com.gmail.mooman219.frame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import com.gmail.mooman219.module.service.player.PlayerData;

public class DataVerifyEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public PlayerData playerData;
    public AsyncPlayerPreLoginEvent event;

    // Called before the player is saved.
    public DataVerifyEvent(AsyncPlayerPreLoginEvent event, PlayerData playerData) {
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
}
