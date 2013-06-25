package com.gmail.mooman219.frame.serialize.json;

import java.util.List;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class BasicItemStack {
    // Default
    @SerializedName("ID") public int id;
    @SerializedName("Amt") public int amount;
    @SerializedName("Dmg") public short damage;
    @SerializedName("Dat") public byte data;
    // ItemMeta
    @SerializedName("Name") public String displayName;
    @SerializedName("Lore") public List<String> lore;
    // BookMeta
    @SerializedName("Auth") public String author;
    @SerializedName("Title") public String title;
    @SerializedName("Pages") public List<String> pages;
    // LeatherArmorMeta
    @SerializedName("Color") public Color armorColor;

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
                armorColor = leatherArmorMeta.getColor();
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
                if(armorColor != null) {
                    leatherArmorMeta.setColor(armorColor);
                }
            }
            itemstack.setItemMeta(itemMeta);
        }
        return itemstack;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static BasicItemStack fromString(String string) {
        return BasicItemStack.getGson().fromJson(string, BasicItemStack.class);
    }

    public static Gson getGson() {
        return new GsonBuilder()
        .create();
    }
}