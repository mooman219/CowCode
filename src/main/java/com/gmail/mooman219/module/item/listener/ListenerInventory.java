package com.gmail.mooman219.module.item.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.EventHelper;
import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.aspect.Aspect;
import com.gmail.mooman219.module.item.api.stockpile.Stockpile;
import com.gmail.mooman219.module.item.event.ButtonClickEvent;

public class ListenerInventory implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        /**/ // UNHOLY AMOUNT OF INVENTORY CLICK INFORMATION
        Loader.info("~~~~~~~~~~~~ Inventory Click Event [Start] ~~~~~~~~~~~~");
        Loader.info("Slot: " + event.getSlot());
        Loader.info("RawSlot: " + event.getRawSlot());
        Loader.info("SlotType: " + event.getSlotType().name());
        Loader.info("CurrentItem: " + event.getCurrentItem());
        Loader.info("Action: " + event.getAction().name());
        Loader.info("Click: " + event.getClick().name());
        Loader.info("Cursor: " + event.getCursor());
        Loader.info("InventoryName: " + event.getInventory().getName());
        Loader.info("ClickedInventoryName: " + (event.getClickedInventory() != null ? event.getClickedInventory().getName() : "null"));
        /**/
        if(!event.isCancelled()) {
            // In most cases, this means they clicked the top inventory.
            if(event.getSlot() == event.getRawSlot()) {
                Stockpile stockpile = Stockpile.getStockpile(event.getClickedInventory());
                if(stockpile != null) {
                    stockpile.onClick(event);
                }
            }
            // The extra isCancelled is in case the stockpile cancels the event.
            if(!event.isCancelled() && !ItemHelper.isNull(event.getCurrentItem())) {
                Aspect item = Aspect.get(event.getCurrentItem());
                if(item.isButton() || item.isUnmoveable()) {
                    event.setCancelled(true);
                    if(item.isButton()) {
                        EventHelper.callEvent(new ButtonClickEvent(CDPlayer.get((Player) event.getWhoClicked()), event.getCurrentItem(), event.getCursor(), event.getClick()));
                    }
                }
            }
        }
    }
}