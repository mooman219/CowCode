package com.gmail.mooman219.frame;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemHelper {
    public static ItemStack setName(int id, String name) {
        return setName(new ItemStack(id), name);
    }

    public static ItemStack setName(ItemStack item, String name) {
        ItemMeta meta = getItemMeta(item);
        meta.setDisplayName(name);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        List<String> list = Arrays.asList(lore);
        ItemMeta meta = getItemMeta(item);
        meta.setLore(list);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack setLore(ItemStack item, List<String> lore) {
        ItemMeta meta = getItemMeta(item);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemMeta getItemMeta(ItemStack item) {
        if(item.hasItemMeta()) {
            return item.getItemMeta();
        } else {
            ItemMeta itemMeta = Bukkit.getItemFactory().getItemMeta(item.getType());
            item.setItemMeta(itemMeta);
            return itemMeta;
        }
    }
}
