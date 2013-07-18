package com.gmail.mooman219.frame.item;

public enum AspectType {
    SWORD(0),
    SHIELD(1),
    BOW(2),
    STAFF(3),
    DAGGER(4),
    HAMMER(5),
    AXE(6),
    SPELLBOOK(7);

    private final int id;

    AspectType(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}