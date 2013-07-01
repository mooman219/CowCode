package com.gmail.mooman219.handler.task.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class TickSecondAsyncEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    public TickSecondAsyncEvent() {
        super(true);
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
