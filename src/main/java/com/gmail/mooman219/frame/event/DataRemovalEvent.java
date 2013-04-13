package com.gmail.mooman219.frame.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DataRemovalEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    public DataRemovalEvent(boolean async, Player player) {
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
    
    public Player getPlayer() {
        return player;
    }
}
