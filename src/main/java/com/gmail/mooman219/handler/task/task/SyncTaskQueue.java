package com.gmail.mooman219.handler.task.task;

import com.gmail.mooman219.handler.task.type.CCSynchronizer;
import com.gmail.mooman219.handler.task.type.CCTask;

public class SyncTaskQueue extends CCSynchronizer<CCTask> {
    public SyncTaskQueue() {
        super("CC SyncTaskQueue");
    }

    @Override
    public Runnable getConsumer() {
        return new Runnable() {
            @Override
            public void run() {
                CCTask task = takeTo();
                task.run();
                putFrom(task);
            }
        };
    }
}
