package com.gmail.mooman219.frame.item.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.gmail.mooman219.frame.text.Chat;

public abstract class Stockpile implements InventoryHolder {
    private static final String prefix = Chat.BOLD + "* " + Chat.RESET;
    private Inventory inventory;

    public Stockpile(int size, String name) {
        inventory = Bukkit.createInventory(this, size, prefix + name);
    }

    public InventoryClickEvent onClick(InventoryClickEvent event) {
        return event;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static boolean isStockpile(Inventory inventory) {
        return inventory != null && inventory.getName().startsWith(prefix) && inventory.getHolder() != null;
    }

    public static Stockpile getStockpile(Inventory inventory) {
        if(isStockpile(inventory)) {
            return (Stockpile) inventory.getHolder();
        }
        return null;
    }
}