package com.gmail.mooman219.module.buff.store;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.layout.PlayerData;

public class PDBuff extends PlayerData {
    public PDBuff(CDPlayer player) {
        super(player, "buff");
    }

    /**
     * Offline
     */

    // No offline data

    /**
     * Live
     */

    @Override
    public void create() {
    }

    @Override
    public void destroy() {
    }
}
