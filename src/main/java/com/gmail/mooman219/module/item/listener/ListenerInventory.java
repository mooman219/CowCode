package com.gmail.mooman219.module.item.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.module.item.api.Aspect;
import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.inventory.Stockpile;

public class ListenerInventory implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onClickStockpile(InventoryClickEvent event) {
        if(Stockpile.isStockpile(event.getClickedInventory())) {
            Stockpile.getStockpile(event.getClickedInventory()).onClick(event);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Loader.info("Slot: " + event.getSlot());
        Loader.info("RawSlot: " + event.getRawSlot());
        Loader.info("SlotType: " + event.getSlotType().name());
        Loader.info("InvName: " + event.getInventory().getName());
        Loader.info("ClickInvName: " + event.getClickedInventory().getName());
        if(!ItemHelper.isNull(event.getCurrentItem())) {
            Aspect item = Aspect.get(event.getCurrentItem());
            if(item.isUnmoveable()) {
                event.setCancelled(true);
            }
        }
    }
}