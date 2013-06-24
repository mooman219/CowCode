package com.gmail.mooman219.module.rpg.item;

import com.gmail.mooman219.frame.ItemHelper;
import com.gmail.mooman219.frame.text.Chat;

public class ItemDefaults {
    public final static Stockpile playerInv = new Stockpile(4);

    static {
        playerInv.set(new String[]{"MH  12345",
                                   "         ",
                                   "         ",
                                   "         ",});
        playerInv.map('M', ItemHelper.setName(371, Chat.BOLD + "" + Chat.GOLD + "Main hand"));
        playerInv.map('H', ItemHelper.setName(371, Chat.BOLD + "" + Chat.GRAY + "Off hand"));
        playerInv.map('1', ItemHelper.setLore(ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 1"), "moo", Chat.GOLD + "man", "219"));
        playerInv.map('2', ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 2"));
        playerInv.map('3', ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 3"));
        playerInv.map('4', ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 4"));
        playerInv.map('5', ItemHelper.setName(371, Chat.BOLD + "" + Chat.PURPLE + "Spell slot 5"));
    }
}
