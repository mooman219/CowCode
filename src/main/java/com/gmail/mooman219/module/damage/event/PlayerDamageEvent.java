package com.gmail.mooman219.module.damage.event;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.damage.api.DamageType;

public class PlayerDamageEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private boolean cancelled = false;
    private final CDPlayer target;
    private final DamageType damageType;
    private double damage;

    public PlayerDamageEvent(CDPlayer target, DamageType damageType, double damage) {
        this.target = target;
        this.damageType = damageType;
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

    public DamageType getDamageType() {
        return damageType;
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
