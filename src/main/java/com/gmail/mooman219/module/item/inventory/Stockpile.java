package com.gmail.mooman219.module.item.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import com.gmail.mooman219.frame.text.Chat;

public abstract class Stockpile implements InventoryHolder {
    private Inventory inventory;

    public Stockpile(int size, String name) {
        inventory = Bukkit.createInventory(this, size, Chat.BOLD + "* " + Chat.RESET + name);
    }

    public InventoryClickEvent onClick(InventoryClickEvent event) {
        return event;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    public static Stockpile getStockpile(Inventory inventory) {
        if(inventory != null && inventory.getName().toLowerCase().startsWith(Chat.BOLD + "* " + Chat.RESET) && inventory.getHolder() != null) {
            return (Stockpile) inventory.getHolder();
        }
        return null;
    }
}