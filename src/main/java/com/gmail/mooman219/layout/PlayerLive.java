package com.gmail.mooman219.layout;

import com.gmail.mooman219.bull.CDPlayer;

/*
 * PlayerLive is really just for any type of data to be stored on a player.
 */
public abstract class PlayerLive {
    private final CDPlayer player;

    public PlayerLive(CDPlayer player) {
        this.player = player;
    }

    public CDPlayer getPlayer() {
        return player;
    }
}
