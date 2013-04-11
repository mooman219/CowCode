package com.gmail.mooman219.handler.task.task;

import com.gmail.mooman219.frame.event.CCEventFactory;

public class TimeKeeperAsync implements Runnable {
    public void run() {
        CCEventFactory.callTickSecondAsyncEvent();
    }
}