package com.gmail.mooman219.frame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.mooman219.module.service.player.PlayerData;

public class DataRemovalEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public PlayerData playerData;

    // Called before the player is saved.
    public DataRemovalEvent(boolean async, PlayerData playerData) {
        super(async);
        this.playerData = playerData;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
