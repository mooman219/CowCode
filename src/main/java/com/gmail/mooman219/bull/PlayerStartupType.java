package com.gmail.mooman219.bull;

public enum PlayerStartupType {
    PRELOGIN(0),
    LOGIN(1),
    JOIN(2);

    private final int id;

    PlayerStartupType(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
