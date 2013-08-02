package com.gmail.mooman219.module.item.stockpile;

import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.inventory.Stockpile;

public class EquipmentStockpile extends Stockpile {
    /**
     * 4  Head
     * 11 Amulet
     * 13 Chest
     * 22 Legs
     * 24 Ring 1
     * 25 Ring 2
     * 28 Main Hand
     * 29 Off Hand
     * 31 Boots
     */
    private static final int[] significantSlots = {4, 11, 13, 22, 24, 25, 28, 29, 31};
    public EquipmentStockpile() {
        super(4, "Equipment");
    }

    @Override
    public ItemStack[] getSignificantItems() {
        ItemStack[] items = new ItemStack[9];
        for(int i = 0; i < significantSlots.length; i++) {
            ItemStack item = getInventory().getContents()[significantSlots[i]];
            items[i] = ItemHelper.isNull(item) ? null : item;
        }
        return items;
    }

    @Override
    public void setSignificantItems(ItemStack... itemStacks) {
        for(int i = 0; i < significantSlots.length && i < itemStacks.length; i++) {
            if(!ItemHelper.isNull(itemStacks[i])) {
                getInventory().setItem(significantSlots[i], itemStacks[i]);
            }
        }
    }
}
