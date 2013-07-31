package com.gmail.mooman219.module.item;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.item.api.Aspect;
import com.gmail.mooman219.module.item.api.ItemHelper;
import com.gmail.mooman219.module.item.api.inventory.Stockpiler;

public class InventoryDefaults {
    private final static Stockpiler playerInventory = new Stockpiler(4); // 4 rows

    static {
        playerInventory.setClearing(false);
        playerInventory.setOverwriting(true);
        playerInventory.set(new String[]{
                "MH  12345",
                "        E",
                "         ",
                "         "}
        );
        Aspect aspect = new Aspect();
        aspect.setUnmoveable(true);
        playerInventory.map('M', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GOLD + "Main hand")));
        playerInventory.map('H', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Off hand")));
        playerInventory.map('1', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 1")));
        playerInventory.map('2', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 2")));
        playerInventory.map('3', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 3")));
        playerInventory.map('4', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 4")));
        playerInventory.map('5', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 5")));
        playerInventory.map('E', aspect.write(ItemHelper.setName(118, Chat.BOLD + "" + Chat.PURPLE + "View Equipment")));
    }

    public static void setupPlayerInventory(PlayerInventory inventory) {
        playerInventory.apply(inventory);
    }

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
