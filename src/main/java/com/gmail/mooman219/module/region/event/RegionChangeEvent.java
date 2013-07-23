package com.gmail.mooman219.module.region.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class RegionChangeEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    private final CDPlayer player;
    private final PlayerMoveEvent event;
    private final BasicRegion oldRegion;
    private final BasicRegion newRegion;

    public RegionChangeEvent(PlayerMoveEvent event, CDPlayer player, BasicRegion oldRegion, BasicRegion newRegion) {
        this.player = player;
        this.event = event;
        this.oldRegion = oldRegion;
        this.newRegion = newRegion;
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

    public PlayerMoveEvent getEvent() {
        return event;
    }

    public BasicRegion getOldRegion() {
        return oldRegion;
    }

    public BasicRegion getNewRegion() {
        return newRegion;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
