package com.gmail.mooman219.module.chat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.module.service.DTPlayer;

public class PLChat {
    private SoftReference<DTPlayer> softLastMessaged;
    public long lastGlobalChat = 0l;

    public final DTPlayer getLastMessaged() {
        if (softLastMessaged == null || softLastMessaged.get() == null) {
            return null;
        }
        return softLastMessaged.get();
    }

    public final void setLastMessaged(DTPlayer lastMessaged) {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
        }
        softLastMessaged = new SoftReference<DTPlayer>(lastMessaged);
    }
}
