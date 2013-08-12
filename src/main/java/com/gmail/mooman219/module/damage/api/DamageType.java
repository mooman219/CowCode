package com.gmail.mooman219.module.damage.api;

import com.gmail.mooman219.frame.text.Chat;

public enum DamageType {
    PHYSICAL(0, Chat.GOLD, "Physical"),
    FIRE(1, Chat.RED, "Fire"),
    POISON(2, Chat.GREEN, "Poison"),
    ICE(3, Chat.BLUE, "Ice"),
    MAGIC(4, Chat.PURPLE, "Magic");

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
