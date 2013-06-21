package com.gmail.mooman219.layout;

import com.gmail.mooman219.bull.CDPlayer;

public abstract class PlayerLive {
    private final CDPlayer player;

    public PlayerLive(CDPlayer player) {
        this.player = player;
    }

    public CDPlayer getPlayer() {
        return player;
    }
}
