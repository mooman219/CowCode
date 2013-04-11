package com.gmail.mooman219.handler.task.queue;

import com.gmail.mooman219.handler.task.type.CCSynchronizer;
import com.gmail.mooman219.handler.task.type.CCTask;

public class SyncTaskQueue extends CCSynchronizer<CCTask> {
    public SyncTaskQueue() {
        super("CC SyncTaskQueue");
    }

    public Runnable getConsumer() {
        return new Runnable() {
            public void run() {
                CCTask task = takeTo();
                task.run();
                putFrom(task);
            }
        };
    }
}
