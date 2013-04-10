package com.gmail.mooman219.craftbukkit;

public class BullObject {
    public BullData bull_live = null;

    public void callTick() {
        try {
            if(bull_live != null) {
                bull_live.onTick();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}