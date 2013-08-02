package com.gmail.mooman219.module.item.api;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.api.inventory.Stockpiler;

public class InventoryHelper {
    private final static Stockpiler defaultPlayerInventory = new Stockpiler(4); // 4 rows

    static {
        defaultPlayerInventory.setClearing(false);
        defaultPlayerInventory.setOverwriting(true);
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
        defaultPlayerInventory.map('E', aspect.write(ItemHelper.setName(118, Chat.BOLD + "" + Chat.PURPLE + "View Equipment")));
    }

    public static void setupPlayerInventory(PlayerInventory inventory) {
        defaultPlayerInventory.apply(inventory);
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
