package com.gmail.mooman219.module.damage.event;

import org.bukkit.event.HandlerList;

import com.gmail.mooman219.bull.CDLiving;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.damage.api.DamageType;

public class PlayerDamageByLivingEvent extends PlayerDamageEvent {
    private static final HandlerList handlers = new HandlerList();

    private final CDLiving damager;

    public PlayerDamageByLivingEvent(CDPlayer target, CDLiving damager, DamageType damageType, double damage) {
        super(target, damageType, damage);
        this.damager = damager;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public CDLiving getDamager() {
        return damager;
    }
}
