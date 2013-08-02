package com.gmail.mooman219.module.item.stockpile;

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
    public int[] getSignificantSlots() {
        return significantSlots;
    }
}
