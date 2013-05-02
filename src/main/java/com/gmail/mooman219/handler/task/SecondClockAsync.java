package com.gmail.mooman219.handler.task;

import com.gmail.mooman219.frame.event.CEventFactory;

public class SecondClockAsync implements Runnable {
    @Override
    public void run() {
        CEventFactory.callTickSecondAsyncEvent();
    }
}