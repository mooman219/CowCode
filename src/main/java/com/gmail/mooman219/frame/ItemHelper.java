package com.gmail.mooman219.frame;

import java.util.Arrays;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHelper {
    public static ItemStack setName(int id, String name) {
        return setName(new ItemStack(id), name);
    }

    public static ItemStack setName(ItemStack item, String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        List<String> list = Arrays.asList(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(list);
        item.setItemMeta(meta);
        return null;
    }
}
