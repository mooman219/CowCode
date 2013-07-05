package com.gmail.mooman219.bull;

public enum PlayerStartupType {
    POST_VERIFY(0),
    PRE_CREATION(1),
    PRE_JOIN(2);

    private final int id;

    PlayerStartupType(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
