package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.text.Chat;

public enum AspectType {
    // Weapon
    SWORD(0, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Sword"),
    SHIELD(1, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Shield"),
    BOW(2, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Bow"),
    STAFF(3, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Staff"),
    DAGGER(4, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Dagger"),
    HAMMER(5, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Hammer"),
    AXE(6, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Axe"),
    SPELLBOOK(7, Chat.GRAY + "Weapon " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Spellbook"),
    // Tool
    PICK_AXE(8, Chat.GRAY + "Tool " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Pick Axe"),
    FISHING_ROD(9, Chat.GRAY + "Tool " + Chat.DARK_GRAY + "-> " + Chat.GRAY + "Fishing Rod");

    private final int id;
    private final String name;

    AspectType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static AspectType fromString(String string) {
        for(AspectType aspect : AspectType.values()) {
            if(string.contains(aspect.getName())) {
                return aspect;
            }
        }
        throw new IllegalArgumentException("Invalid name of " + string);
    }
}