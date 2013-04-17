package com.gmail.mooman219.module.chat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.module.DLPlayer;

public class PLChat {
    private SoftReference<DLPlayer> softLastMessaged;
    public long lastGlobalChat = 0l;

    public final DLPlayer getLastMessaged() {
        if (softLastMessaged == null || softLastMessaged.get() == null) {
            return null;
        }
        return softLastMessaged.get();
    }

    public final void setLastMessaged(DLPlayer lastMessaged) {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
        }
        softLastMessaged = new SoftReference<DLPlayer>(lastMessaged);
    }
}
