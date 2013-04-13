package com.gmail.mooman219.frame.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerLoginEvent;

public class DataCreateEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;
    private final PlayerLoginEvent event;

    public DataCreateEvent(PlayerLoginEvent event, Player player) {
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

    public Player getPlayer() {
        return player;
    }

    public PlayerLoginEvent getEvent() {
        return event;
    }
}
