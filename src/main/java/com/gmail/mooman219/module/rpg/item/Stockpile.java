package com.gmail.mooman219.module.rpg.item;

import java.util.HashMap;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Stockpile {
    private HashMap<Character, ItemStack> charList;
    private Character[][] charMap;

    public Stockpile(int rows) {
        charMap = new Character[rows][9];
        charList = new HashMap<Character, ItemStack>();
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
                ItemStack item = charList.get(charMap[y][x]);
                int slot = y * 9 + x;
                if(slot >= inventory.getSize()) {
                    return;
                } else if(item == null) {
                    inventory.clear(slot);
                } else {
                    inventory.setItem(slot, item);
                }
            }
        }
    }
}
