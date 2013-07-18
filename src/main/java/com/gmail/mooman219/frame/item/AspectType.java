package com.gmail.mooman219.frame.item;

import com.gmail.mooman219.frame.text.Chat;

public enum AspectType {
    WEAPON(0, Chat.GRAY + "Weapon"),
    TOOL(7, Chat.GRAY + "Tool");

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
}