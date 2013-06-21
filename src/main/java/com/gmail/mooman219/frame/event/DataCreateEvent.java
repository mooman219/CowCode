package com.gmail.mooman219.frame.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;

import com.gmail.mooman219.bull.CDPlayer;

public class DataCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer player;
    private final PlayerLoginEvent event;

    public DataCreateEvent(PlayerLoginEvent event, CDPlayer player) {
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

    public PlayerLoginEvent getEvent() {
        return event;
    }
}
