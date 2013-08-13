package com.gmail.mooman219.module.item.api.stockpile;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.module.item.api.ItemHelper;

public abstract class Stockpile implements InventoryHolder {
    private static final String prefix = "§χ§0";
    private Inventory inventory;

    public Stockpile(int rows, String name) {
        inventory = Bukkit.createInventory(this, rows * 9, prefix + name);
    }

    public abstract int[] getSignificantSlots();

    public abstract boolean isSlotSignificant(int slot);

    public void onClick(InventoryClickEvent event) {}

    @Override
    public final Inventory getInventory() {
        return inventory;
    }

    public final ItemStack[] getSignificantItems() {
        ItemStack[] items = new ItemStack[getSignificantSlots().length];
        for(int i = 0; i < getSignificantSlots().length; i++) {
            ItemStack item = getInventory().getContents()[getSignificantSlots()[i]];
            items[i] = ItemHelper.isNull(item) ? null : item;
        }
        return items;
    }

    public final void setSignificantItems(ItemStack... itemStacks) {
        for(int i = 0; i < getSignificantSlots().length && i < itemStacks.length; i++) {
            if(!ItemHelper.isNull(itemStacks[i])) {
                getInventory().setItem(getSignificantSlots()[i], itemStacks[i]);
            }
        }
    }

    public static boolean isStockpile(Inventory inventory) {
        return inventory != null && inventory.getHolder() != null && inventory.getName().startsWith(prefix);
    }

    public static Stockpile getStockpile(Inventory inventory) {
        if(isStockpile(inventory)) {
            return (Stockpile) inventory.getHolder();
        }
        return null;
    }
}