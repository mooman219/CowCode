package com.gmail.mooman219.module.item.api.inventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public abstract class Stockpile implements InventoryHolder {
    private static final String prefix = "* ";
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
    
    public ItemStack[] getSignificantItems() {
        return inventory.getContents();
    }
    
    public void setSignificantItems(ItemStack... itemStacks) {
        this.inventory.setContents(itemStacks);
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