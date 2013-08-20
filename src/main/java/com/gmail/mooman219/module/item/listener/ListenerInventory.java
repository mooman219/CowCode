package com.gmail.mooman219.module.item.listener;

import java.util.Iterator;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.EventHelper;
import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.aspect.Aspect;
import com.gmail.mooman219.module.item.api.event.ButtonClickEvent;
import com.gmail.mooman219.module.item.api.stockpile.Stockpile;

public class ListenerInventory implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        /**/ // UNHOLY AMOUNT OF INVENTORY CLICK INFORMATION
        Loader.info("~~~~~~~~~~~~ Inventory Click Event ~~~~~~~~~~~~");
        Loader.info("Slot: " + event.getSlot() + " - Raw: " + event.getRawSlot());
        Loader.info("SlotType: " + event.getSlotType().name());
        Loader.info("HotbarButton: " + event.getHotbarButton());
        Loader.info("HotbarItem: " + (event.getHotbarButton() > -1 && !ItemHelper.isNull(event.getWhoClicked().getInventory().getItem(event.getHotbarButton())) ? event.getWhoClicked().getInventory().getItem(event.getHotbarButton()).toString() : "None"));
        Loader.info("Action: " + event.getAction().name());
        Loader.info("Click: " + event.getClick().name());
        Loader.info("ClickedItem: " + event.getCurrentItem());
        Loader.info("HeldItem: " + event.getCursor());
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
        Loader.info("[" + event.isCancelled() + "]");
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDrag(InventoryDragEvent event) {
        /**/
        Loader.info("~~~~~~~~~~~~   Inventory Drag Event   ~~~~~~~~~~~~");
        Loader.info("Type: " + event.getType().name());
        Loader.info("Slots: " + event.getInventorySlots().toString());
        Loader.info("RawSlots: " + event.getRawSlots().toString());
        Loader.info("Items: " + event.getNewItems().values().toString());
        Loader.info("NewHeldItem: " + event.getCursor());
        Loader.info("OldHeldItem: " + event.getOldCursor());
        Loader.info("InventoryName: " + event.getInventory().getName());
        /**/
        if(!event.isCancelled()) {
            Stockpile stockpile = Stockpile.getStockpile(event.getInventory());
            if(stockpile != null) {
                // This checks if the drag event modifies the stockpile inventory.
                Iterator<Integer> inventorySlots = event.getInventorySlots().iterator();
                while(inventorySlots.hasNext()) {
                    if(event.getRawSlots().contains(inventorySlots.next())) {
                        stockpile.onDrag(event);
                        break;
                    }
                }
            }
        }
    }
}