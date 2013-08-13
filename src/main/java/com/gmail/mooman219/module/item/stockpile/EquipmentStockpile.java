package com.gmail.mooman219.module.item.stockpile;

import java.util.HashSet;

import org.bukkit.event.inventory.InventoryClickEvent;

import com.gmail.mooman219.module.item.api.InventoryHelper;
import com.gmail.mooman219.module.item.api.stockpile.Stockpile;

public class EquipmentStockpile extends Stockpile {
    /**
     * 0 - 4  = Head
     * 1 - 11 = Amulet
     * 2 - 13 = Chest
     * 3 - 22 = Legs
     * 4 - 24 = Ring 1
     * 5 - 25 = Ring 2
     * 6 - 28 = Main Hand
     * 7 - 29 = Off Hand
     * 8 - 31 = Boots
     */
    private static final int[] significantSlots = {4, 11, 13, 22, 24, 25, 28, 29, 31};
    private static final HashSet<Integer> slots = new HashSet<Integer>();

    static {
        for(int i : significantSlots) {
            slots.add(i);
        }
    }

    public EquipmentStockpile() {
        super(4, "Equipment");
        InventoryHelper.applyDefaultEquipment(getInventory());
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        if(!isSlotSignificant(event.getSlot())) {
            event.setCancelled(true);
        }
        
    }

    @Override
    public boolean isSlotSignificant(int slot) {
        return slots.contains(slot);
    }

    @Override
    public int[] getSignificantSlots() {
        return significantSlots;
    }
}
