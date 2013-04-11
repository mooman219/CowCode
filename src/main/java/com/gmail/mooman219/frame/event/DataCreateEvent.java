package com.gmail.mooman219.frame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;

import com.gmail.mooman219.module.service.player.PlayerData;

public class DataCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public PlayerData playerData;
    public PlayerLoginEvent event;

    // Called before the player is saved.
    public DataCreateEvent(PlayerLoginEvent event, PlayerData playerData) {
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
