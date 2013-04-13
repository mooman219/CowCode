package com.gmail.mooman219.module.chat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.module.service.PlayerData;

public class PLChat {
    private SoftReference<PlayerData> softLastMessaged;
    public long lastGlobalChat = 0l;

    public final PlayerData getLastMessaged() {
        if (softLastMessaged == null || softLastMessaged.get() == null) {
            return null;
        }
        return softLastMessaged.get();
    }

    public final void setLastMessaged(PlayerData lastMessaged) {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
        }
        softLastMessaged = new SoftReference<PlayerData>(lastMessaged);
    }
}
