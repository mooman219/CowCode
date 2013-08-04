package com.gmail.mooman219.module.item.stockpile;

import com.gmail.mooman219.module.item.api.inventory.Stockpile;

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

    public EquipmentStockpile() {
        super(4, "Equipment");
    }

    @Override
    public int[] getSignificantSlots() {
        return significantSlots;
    }
}
