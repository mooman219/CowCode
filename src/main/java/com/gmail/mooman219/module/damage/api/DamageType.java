package com.gmail.mooman219.module.damage.api;

import com.gmail.mooman219.frame.text.Chat;

public enum DamageType {
    PHYSICAL(0, Chat.GOLD, "Physical"),
    RANGED(1, Chat.GOLD, "Ranged"),

    FIRE(2, Chat.RED, "Fire"),
    POISON(3, Chat.GREEN, "Poison"),
    ICE(4, Chat.BLUE, "Ice"),
    LIGHTNING(5, Chat.WHITE, "Lightning"),
    MAGIC(6, Chat.PURPLE, "Magic");

    private final int id;
    private final String prefix;
    private final String name;

    DamageType(int id, Chat prefix, String name) {
        this(id, prefix + "", name);
    }

    DamageType(int id, String prefix, String name) {
        this.id = id;
        this.prefix = prefix;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return prefix + name;
    }
}
