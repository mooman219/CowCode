package com.gmail.mooman219.module.rpg.item;

import org.bukkit.inventory.ItemStack;
import com.gmail.mooman219.frame.ItemHelper;
import com.gmail.mooman219.frame.text.Chat;

public class ItemDefaults {
    /**
    public final static ItemStack[] hotbar = {new ItemStack(118), new ItemStack(118), null, null, new ItemStack(118), new ItemStack(118), new ItemStack(118), new ItemStack(118), new ItemStack(118)};
    
    static {
        ItemMeta meta;
        meta = hotbar[0].getItemMeta(); meta.setDisplayName(Chat.GOLD + "Main hand");    hotbar[0].setItemMeta(meta);
        meta = hotbar[1].getItemMeta(); meta.setDisplayName(Chat.GRAY + "Off hand");     hotbar[1].setItemMeta(meta);
        //
        //
        meta = hotbar[4].getItemMeta(); meta.setDisplayName(Chat.PURPLE + "Spell slot 1"); hotbar[4].setItemMeta(meta);
        meta = hotbar[5].getItemMeta(); meta.setDisplayName(Chat.PURPLE + "Spell slot 2"); hotbar[5].setItemMeta(meta);
        meta = hotbar[6].getItemMeta(); meta.setDisplayName(Chat.PURPLE + "Spell slot 3"); hotbar[6].setItemMeta(meta);
        meta = hotbar[7].getItemMeta(); meta.setDisplayName(Chat.PURPLE + "Spell slot 4"); hotbar[7].setItemMeta(meta);
        meta = hotbar[8].getItemMeta(); meta.setDisplayName(Chat.PURPLE + "Spell slot 5"); hotbar[8].setItemMeta(meta);
        meta = null;
    }
    
    public static void force(CDPlayer player) {
        for(int i = 0; i < 9; i++) {
            if(hotbar[i] != null) {                
                player.getPlayer().getInventory().setItem(i, hotbar[i].clone());
            }
        }
    }
    /**/
    public final static Stockpile playerInv = new Stockpile(4);
    
    static {
        playerInv.set(new String[]{"MH  12345",
                                   "        H",
                                   "        1",
                                   "         ",});
        ItemStack item;
        item = ItemHelper.setName(new ItemStack(118), Chat.GOLD + "Main hand");      playerInv.map('M', item);
        item = ItemHelper.setName(new ItemStack(118), Chat.GRAY + "Off hand");       playerInv.map('H', item);
        item = ItemHelper.setName(new ItemStack(118), Chat.PURPLE + "Spell slot 1"); playerInv.map('1', item);
        item = ItemHelper.setName(new ItemStack(118), Chat.PURPLE + "Spell slot 2"); playerInv.map('2', item);
        item = ItemHelper.setName(new ItemStack(118), Chat.PURPLE + "Spell slot 3"); playerInv.map('3', item);
        item = ItemHelper.setName(new ItemStack(118), Chat.PURPLE + "Spell slot 4"); playerInv.map('4', item);
        item = ItemHelper.setName(new ItemStack(118), Chat.PURPLE + "Spell slot 5"); playerInv.map('5', item);
        item = null;
    }
}
