package com.gmail.mooman219.module.item.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.mooman219.frame.item.Aspect;
import com.gmail.mooman219.frame.item.ItemHelper;

public class ListenerInventory implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onClick(InventoryClickEvent event) {
        if(!ItemHelper.isNull(event.getCurrentItem())) {
            Aspect item = Aspect.get(event.getCurrentItem());
            if(item.isUnmoveable()) {
                event.setCancelled(true);
            }
        }
    }
}