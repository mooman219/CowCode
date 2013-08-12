package com.gmail.mooman219.module.damage.event;

import org.bukkit.event.HandlerList;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.damage.api.DamageType;

public class PlayerDamageByPlayerEvent extends PlayerDamageEvent {
    private static final HandlerList handlers = new HandlerList();

    private final CDPlayer damager;

    public PlayerDamageByPlayerEvent(CDPlayer target, CDPlayer damager, DamageType damageType, double damage) {
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

    public CDPlayer getDamager() {
        return damager;
    }
}
