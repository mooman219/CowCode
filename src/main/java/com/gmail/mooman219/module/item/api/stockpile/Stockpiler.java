package com.gmail.mooman219.module.item.api.stockpile;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.module.item.api.ItemHelper;

public class Stockpiler {
    private HashMap<Character, ItemStack> charList;
    private Character[][] charMap;

    private boolean isOverwriting = false;
    private boolean isClearing = false;

    public Stockpiler(int rows) {
        this(rows, false, false);
    }

    public Stockpiler(int rows, boolean isOverwriting, boolean isClearing) {
        charMap = new Character[rows][9];
        charList = new HashMap<Character, ItemStack>();
        this.isOverwriting = isOverwriting;
        this.isClearing = isClearing;
    }

    public void set(String[] characters) {
        for(int y = 0; y < charMap.length; y++) {
            for(int x = 0; x < charMap[y].length; x++) {
                if(characters.length <= y || characters[y].length() <= x) {
                    charMap[y][x] = null;
                } else {
                    charMap[y][x] = characters[y].charAt(x);
                }
            }
        }
    }

    public void map(Character character, ItemStack item) {
        if(item == null) {
            charList.remove(character);
        } else {
            charList.put(character, item.clone());
        }
    }

    public void apply(Inventory inventory) {
        for(int y = 0; y < charMap.length; y++) {
            for(int x = 0; x < charMap[y].length; x++) {
                int slot = y * 9 + x;
                ItemStack item = charList.get(charMap[y][x]);
                ItemStack oldItem = inventory.getItem(slot);
                if(slot >= inventory.getSize()) {
                    return;
                } else if(ItemHelper.isNull(item)) {
                    if(isClearing) {
                        inventory.clear(slot);
                    }
                } else if(ItemHelper.isNull(oldItem) || isOverwriting){
                    inventory.setItem(slot, item);
                }
            }
        }
    }

    public boolean isOverwriting() {
        return isOverwriting;
    }

    public void setOverwriting(boolean isOverwriting) {
        this.isOverwriting = isOverwriting;
    }

    public boolean isClearing() {
        return isClearing;
    }

    public void setClearing(boolean isClearing) {
        this.isClearing = isClearing;
    }
}
