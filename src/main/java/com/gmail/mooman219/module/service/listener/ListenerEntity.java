package com.gmail.mooman219.module.service.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import com.gmail.mooman219.bull.CDLiving;

public class ListenerEntity implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeath(EntityDeathEvent event) {
        if(!(event.getEntity() instanceof Player)) {
            CDLiving.unload(event.getEntity());
        }
    }
}
