package com.gmail.mooman219.module.service.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.mooman219.bull.CDPlayer;

public class DataRemovalEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer player;

    public DataRemovalEvent(boolean async, CDPlayer player) {
        super(async);
        this.player = player;
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
}
