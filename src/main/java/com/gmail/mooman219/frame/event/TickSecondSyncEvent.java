package com.gmail.mooman219.frame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TickSecondSyncEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public TickSecondSyncEvent() {
        super();
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
