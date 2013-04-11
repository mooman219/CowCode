package com.gmail.mooman219.handler.task.queue;

import com.gmail.mooman219.handler.task.type.CCLinkedBlockingQueue;
import com.gmail.mooman219.handler.task.type.CCTask;

public class AsyncTaskQueue extends CCLinkedBlockingQueue<CCTask>{
    public AsyncTaskQueue() {
        super(true, "CC AsyncTaskQueue");
    }

    public Runnable getConsumer() {
        return new Runnable() {
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
