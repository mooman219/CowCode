package com.gmail.mooman219.frame.serialize;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.google.gson.Gson;

public class SerializeHelper {
    public static class BasicItemStack {
        // Default
        public int id; // ID
        public int amount; // Amount
        public short damage; // Damage
        public byte data; // Data
        // ItemMeta
        public String displayName; // Display Name
        public List<String> lore; // Lore
        // BookMeta
        public String author; // Author
        public String title; // Title
        public List<String> pages; // Pages
        // LeatherArmorMeta
        public Color color; // Armor Color

        public BasicItemStack(ItemStack itemstack) {
            id = itemstack.getTypeId();
            damage = itemstack.getDurability();
            amount = itemstack.getAmount();
            data = itemstack.getData().getData();
            if(itemstack.getItemMeta() != null) {
                ItemMeta itemMeta = itemstack.getItemMeta();
                if(itemMeta.hasDisplayName()) {
                    displayName = itemMeta.getDisplayName();
                }
                if(itemMeta.hasLore()) {                    
                    lore = itemMeta.getLore();
                }
                if(itemMeta instanceof BookMeta) {
                    BookMeta bookMeta = (BookMeta) itemMeta;
                    if(bookMeta.hasAuthor()) {
                        author = bookMeta.getAuthor();
                    }
                    if(bookMeta.hasTitle()) {
                        title = bookMeta.getTitle();
                    }
                    if(bookMeta.hasPages()) {
                        pages = bookMeta.getPages();
                    }
                } else if(itemMeta instanceof LeatherArmorMeta) {
                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                    // getColor is never null
                    color = leatherArmorMeta.getColor();
                }
            }
        }

        public ItemStack toItemStack() {
            ItemStack itemstack = new ItemStack(id, amount, damage);
            itemstack.getData().setData(data);
            if(itemstack.getItemMeta() != null) {
                ItemMeta itemMeta = itemstack.getItemMeta();
                if(displayName != null) {
                    itemMeta.setDisplayName(displayName);
                }
                if(lore != null) {
                    itemMeta.setLore(lore);
                }
                if(itemMeta instanceof BookMeta) {
                    BookMeta bookMeta = (BookMeta) itemMeta;
                    if(author != null) {
                        bookMeta.setAuthor(author);
                    }
                    if(title != null) {
                        bookMeta.setTitle(title);
                    }
                    if(pages != null) {
                        bookMeta.setPages(pages);
                    }
                } else if(itemMeta instanceof LeatherArmorMeta) {
                    LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemMeta;
                    if(color != null) {                        
                        leatherArmorMeta.setColor(color);
                    }
                }
                itemstack.setItemMeta(itemMeta);
            }
            return itemstack;
        }
    }

    public static String toStringFromItemStack(ItemStack itemstack) {
        Gson gson = new Gson();
        String json = gson.toJson(new BasicItemStack(itemstack));
        return json;
    }

    public static ItemStack toItemStackFromString(String string) {
        Gson gson = new Gson();
        BasicItemStack basicItemStack = gson.fromJson(string, BasicItemStack.class);
        return basicItemStack.toItemStack();
    }
}
