package com.gmail.mooman219.module.item.api;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.gmail.mooman219.frame.text.TextHelper;

public class ItemHelper {
    public static ItemStack setName(Material material, String name) {
        return setName(new ItemStack(material), name);
    }

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
        return setLore(item, Arrays.asList(lore));
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

    public static String getDisplayName(ItemStack item) {
        ItemMeta meta = getItemMeta(item);
        if(meta.hasDisplayName()) {
            return meta.getDisplayName();
        } else {
            return TextHelper.toTitleCase(item.getType().name().replace("_", " "));
        }
    }

    public static boolean isNull(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() == 0;
    }

    /**
     * InventoryClickEvent helper methods \/
     */

    public static int getNewSlotRaw(InventoryClickEvent event) {
        if(isNull(event.getCurrentItem())) {
            return event.getRawSlot();
        } else if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            int firstEmpty;
            if(event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
                firstEmpty = event.getView().getBottomInventory().firstEmpty();
            } else {
                firstEmpty = event.getView().getTopInventory().firstEmpty();
            }
            return firstEmpty != -1 ? firstEmpty : event.getRawSlot();
        }
        return event.getRawSlot();
    }
    
    public static int getNewSlot(InventoryClickEvent event) {
        if(isNull(event.getCurrentItem())) {
            return event.getSlot();
        } else if(event.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
            int firstEmpty;
            if(event.getClickedInventory() != null && event.getClickedInventory().equals(event.getView().getTopInventory())) {
                //firstEmpty = event.getView().getBottomInventory().;
                firstEmpty = 0;
            } else {
                firstEmpty = event.getView().getTopInventory().firstEmpty();
            }
            return firstEmpty != -1 ? firstEmpty : event.getSlot();
        }
        return event.getSlot();
    }

    public static ItemStack getNewItem(InventoryClickEvent event) {
        // Shift click overrides all
        if(event.getClick() == ClickType.SHIFT_LEFT || event.getClick() == ClickType.SHIFT_RIGHT) {
            return event.getCurrentItem();
        // Cursor overrides hotbar
        } else if(!isNull(event.getCursor())) {
            return event.getCursor();
        // Hotbar is stupid
        } else if(event.getHotbarButton() > -1 && !isNull(event.getWhoClicked().getInventory().getItem(event.getHotbarButton()))) {
            return event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
        } else {
            return null;
        }
    }
}
