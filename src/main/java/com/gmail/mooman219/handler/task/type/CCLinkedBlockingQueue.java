package com.gmail.mooman219.handler.task.type;

import java.util.concurrent.LinkedBlockingQueue;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.task.CHTask;

public abstract class CCLinkedBlockingQueue<E> {
    private LinkedBlockingQueue<E> linkedBlockingQueue;
    private CCThread processThread;
    private final boolean isImportant;
    private final int size = 1000;
    private final String name;

    public CCLinkedBlockingQueue(boolean isImportant, String name) {
        this.isImportant = isImportant;
        this.name = name;
    }

    public final void start() {
        if(linkedBlockingQueue != null || processThread != null) {
            stop();
        }
        linkedBlockingQueue = new LinkedBlockingQueue<E>(size);
        processThread = CHTask.manager.runThread(getConsumer(), false, 0l, 0l);
        processThread.setName(name);
        Loader.info(CHTask.cast + "[" + name + "] ArrayQueue start");
    }

    public final void stop() {
        if(linkedBlockingQueue != null) {
            if(isImportant) {
                Runnable consumer = getConsumer();
                while(linkedBlockingQueue.size() > 1) {
                    consumer.run();
                }
            }
            linkedBlockingQueue.clear();
            linkedBlockingQueue = null;
        }
        if(processThread != null) {
            CHTask.manager.removeThread(processThread);
            processThread = null;
        }
    }

    public final void put(E object) {
        try {
            linkedBlockingQueue.put(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final E take() {
        try {
            return linkedBlockingQueue.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract Runnable getConsumer();
}
