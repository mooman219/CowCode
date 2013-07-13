package com.gmail.mooman219.module.damage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;

public class ListenerEntity implements Listener {
    @EventHandler()
    public void onDamage (EntityDamageEvent e){

    }

    @EventHandler()
    public void onHeal (EntityRegainHealthEvent e){

    }
}
