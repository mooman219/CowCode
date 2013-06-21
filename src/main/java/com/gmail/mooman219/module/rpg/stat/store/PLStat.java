package com.gmail.mooman219.module.rpg.stat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.layout.PlayerLive;

public class PLStat extends PlayerLive {
    private SoftReference<CDPlayer> softLastMessaged;
    public long lastGlobalChat = 0l;

    public PLStat(CDPlayer player) {
        super(player);
    }

    public CDPlayer getLastMessaged() {
        if (softLastMessaged == null || softLastMessaged.get() == null) {
            return null;
        }
        return softLastMessaged.get();
    }

    public void setLastMessaged(CDPlayer lastMessaged) {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
        }
        softLastMessaged = new SoftReference<CDPlayer>(lastMessaged);
    }
}
