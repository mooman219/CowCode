package com.gmail.mooman219.handler.task;

import java.util.concurrent.SynchronousQueue;

public class PluginSyncQueue {
    private SynchronousQueue<Runnable> waitingQueue;
    private SynchronousQueue<Runnable> finishedQueue;
    private Thread queueThread;

    public PluginSyncQueue() {
        waitingQueue = new SynchronousQueue<Runnable>();
        finishedQueue = new SynchronousQueue<Runnable>();
    }

    public void start() {
        queueThread = CHTask.manager.runThread(new Consumer(), 0l, 0l);
        queueThread.setName("CC SyncQueue");
    }

    public Runnable put(Runnable task) {
        try {
            waitingQueue.put(task);
            return finishedQueue.take();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return task;
    }

    private void process() {
        try {
            Runnable task = waitingQueue.take();
            task.run();
            finishedQueue.put(task);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private class Consumer implements Runnable {
        @Override
        public void run() {
            process();
        }
    }
}