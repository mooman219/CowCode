package com.gmail.mooman219.module.damage.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent;
import com.gmail.mooman219.bull.CDPlayer;

public class PlayerDamageEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    private final CDPlayer target;
    private final EntityDamageEvent event;
    private double damage;

    public PlayerDamageEvent(CDPlayer target, EntityDamageEvent event, double damage) {
        this.target = target;
        this.event = event;
        this.damage = damage;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CDPlayer getTarget() {
        return target;
    }

    public EntityDamageEvent getEvent() {
        return event;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }
}
