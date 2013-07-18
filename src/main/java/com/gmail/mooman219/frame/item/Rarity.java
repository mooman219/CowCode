package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.NumberHelper;
import com.gmail.mooman219.frame.text.Chat;

public enum Rarity {
    COMMON(0, Chat.GRAY),
    UNCOMMON(1, Chat.GREEN),
    RARE(2, Chat.BLUE),
    EPIC(3, Chat.RED),
    LEGENDARY(4, Chat.GOLD);

    private final int id;
    private final Chat color;

    Rarity(int id, Chat color) {
        this.id = id;
        this.color = color;
    }

    public int getID() {
        return id;
    }

    public Chat getColor() {
        return color;
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
}