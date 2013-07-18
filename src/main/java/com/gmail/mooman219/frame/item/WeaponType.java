package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.text.Chat;

public enum WeaponType {
    SWORD(0, Chat.GRAY + "Sword"),
    SHIELD(1, Chat.GRAY + "Shield"),
    BOW(2, Chat.GRAY + "Bow"),
    STAFF(3, Chat.GRAY + "Staff"),
    DAGGER(4, Chat.GRAY + "Dagger"),
    HAMMER(5, Chat.GRAY + "Hammer"),
    AXE(6, Chat.GRAY + "Axe"),
    SPELLBOOK(7, Chat.GRAY + "Spellbook");

    private final int id;
    private final String name;

    WeaponType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}