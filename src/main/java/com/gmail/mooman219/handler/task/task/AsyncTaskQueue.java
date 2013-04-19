package com.gmail.mooman219.handler.task.task;

import com.gmail.mooman219.handler.task.type.CCLinkedBlockingQueue;
import com.gmail.mooman219.handler.task.type.CCTask;

public class AsyncTaskQueue extends CCLinkedBlockingQueue<CCTask>{
    public AsyncTaskQueue() {
        super(true, "CC AsyncTaskQueue");
    }

    @Override
    public Runnable getConsumer() {
        return new Runnable() {
            @Override
            public void run() {
                try {
                    take().run();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
