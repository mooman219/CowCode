package com.gmail.mooman219.module.chat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.bull.CDPlayer;

public class PLChat {
    private SoftReference<CDPlayer> softLastMessaged;
    public long lastGlobalChat = 0l;

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
