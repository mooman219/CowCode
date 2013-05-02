package com.gmail.mooman219.handler.task;

import com.gmail.mooman219.frame.event.CEventFactory;

public class SecondClockSync implements Runnable {
    @Override
    public void run() {
        CEventFactory.callTickSecondSyncEvent();
    }
}