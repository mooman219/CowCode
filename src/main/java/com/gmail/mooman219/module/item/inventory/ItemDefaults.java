package com.gmail.mooman219.module.item.inventory;

import com.gmail.mooman219.frame.item.Aspect;
import com.gmail.mooman219.frame.item.ItemHelper;
import com.gmail.mooman219.frame.text.Chat;

public class ItemDefaults {
    public final static Stockpiler playerInv = new Stockpiler(4); // 4 rows

    static {
        playerInv.set(new String[]{"MH  12345",
                                   "        E",
                                   "         ",
                                   "         "});
        Aspect aspect = new Aspect();
        aspect.setUnmoveable(true);
        playerInv.map('M', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GOLD + "Main hand")));
        playerInv.map('H', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Off hand")));
        playerInv.map('2', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 1")));
        playerInv.map('2', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 2")));
        playerInv.map('3', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 3")));
        playerInv.map('4', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 4")));
        playerInv.map('5', aspect.write(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 5")));
        playerInv.map('E', aspect.write(ItemHelper.setName(118, Chat.BOLD + "" + Chat.PURPLE + "View Equipment")));
    }
}
