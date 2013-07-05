package com.gmail.mooman219.bull;

public enum PlayerShutdownType {
    POST_QUIT(0),
    POST_REMOVAL(1);

    private final int id;

    PlayerShutdownType(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }
}
