package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.text.Chat;

public enum Rarity {
    COMMON(0, Chat.GRAY, "Common"),
    UNCOMMON(1, Chat.GREEN, "Uncommon"),
    RARE(2, Chat.BLUE, "Rare"),
    EPIC(3, Chat.RED, "Epic"),
    LEGENDARY(4, Chat.GOLD, "Legendary");

    private final int id;
    private final Chat color;
    private final String name;

    Rarity(int id, Chat color, String name) {
        this.id = id;
        this.color = color;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public Chat getColor() {
        return color;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return color + name;
    }

    public static Rarity nextRarity() {
        double chance = NumberHelper.random();
        if(chance <= 0.65D) {
            return Rarity.COMMON;
        } else if(chance <= 0.85D) {
            return Rarity.UNCOMMON;
        } else if(chance <= 0.95D) {
            return Rarity.RARE;
        } else if(chance <= 0.99D) {
            return Rarity.EPIC;
        } else if(chance <= 1.0D) {
            return Rarity.LEGENDARY;
        }
        return Rarity.COMMON;
    }

    public static Rarity fromString(String string) {
        for(Rarity rarity : Rarity.values()) {
            if(string.toLowerCase().contains(rarity.getName().toLowerCase())) {
                return rarity;
            }
        }
        throw new IllegalArgumentException("Invalid name of " + string);
    }
}