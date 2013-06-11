package com.gmail.mooman219.frame;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHelper {
    public static ItemStack setName(ItemStack item, String name) {
        ItemMeta meta;
        meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }
}
