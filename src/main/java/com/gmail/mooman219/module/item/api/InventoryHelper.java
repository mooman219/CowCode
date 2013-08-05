package com.gmail.mooman219.module.item.api;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.api.aspect.Aspect;
import com.gmail.mooman219.module.item.api.stockpile.Stockpiler;

public class InventoryHelper {
    private final static Stockpiler defaultPlayerInventory = new Stockpiler(4);
    private final static Stockpiler defaultEquipmentInventory = new Stockpiler(4);

    static {
        defaultPlayerInventory.setClearing(false);
        defaultPlayerInventory.setOverwriting(false);
        defaultPlayerInventory.set(new String[]{
                "MH  12345",
                "        E",
                "         ",
                "         "}
        );
        Aspect aspect = new Aspect();
        aspect.setUnmoveable(true);
        defaultPlayerInventory.map('M', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GOLD + "Main hand")));
        defaultPlayerInventory.map('H', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Off hand")));
        defaultPlayerInventory.map('1', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 1")));
        defaultPlayerInventory.map('2', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 2")));
        defaultPlayerInventory.map('3', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 3")));
        defaultPlayerInventory.map('4', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 4")));
        defaultPlayerInventory.map('5', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 5")));
        aspect.setButton(true);
        defaultPlayerInventory.map('E', aspect.write(ItemHelper.setName(119, Chat.BOLD + "" + Chat.PURPLE + "View Equipment")));

        defaultEquipmentInventory.setClearing(false);
        defaultEquipmentInventory.setOverwriting(false);
        defaultEquipmentInventory.set(new String[]{
                "    H    ",
                "  A C    ",
                "    L 12 ",
                " MO F    "}
        );
        aspect.setButton(false);
        defaultEquipmentInventory.map('H', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Helmet")));
        defaultEquipmentInventory.map('A', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Amulet")));
        defaultEquipmentInventory.map('C', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Chest")));
        defaultEquipmentInventory.map('L', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Leggings")));
        defaultEquipmentInventory.map('1', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Ring 1")));
        defaultEquipmentInventory.map('2', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Ring 2")));
        defaultEquipmentInventory.map('M', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Main hand")));
        defaultEquipmentInventory.map('O', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Off hand")));
        defaultEquipmentInventory.map('F', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Footwear")));
    }

    public static void applyPlayerInventory(PlayerInventory inventory) {
        defaultPlayerInventory.apply(inventory);
    }

    public static void applyDefaultEquipment(Inventory inventory) {
        defaultEquipmentInventory.apply(inventory);
    }

    /**
     * This will not remove buttons, even though they are unmoveable.
     */
    public static void removeUnmoveables(Inventory inventory) {
        for(ItemStack itemStack : inventory.getContents()) {
            if(!ItemHelper.isNull(itemStack)) {
                Aspect aspect = Aspect.get(itemStack);
                if(aspect.isUnmoveable()) {
                    inventory.remove(itemStack);
                }
            }
        }
    }
}
