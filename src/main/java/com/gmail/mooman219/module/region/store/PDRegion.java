package com.gmail.mooman219.module.region.store;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.layout.PlayerData;

public class PDRegion extends PlayerData {
    public PDRegion(CDPlayer player) {
        super(player, "region");
    }

    /**
     * Offline
     */

    // No offline data

    /**
     * Live
     */

    public BasicRegion currentRegion;

    @Override
    public void create() {
        currentRegion = null;
    }

    @Override
    public void destroy() {
        currentRegion = null;
    }
}
