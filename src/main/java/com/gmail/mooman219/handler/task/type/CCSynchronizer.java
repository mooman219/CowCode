package com.gmail.mooman219.handler.task.type;

import java.util.concurrent.SynchronousQueue;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.task.CHTask;

public abstract class CCSynchronizer<E> {
    private SynchronousQueue<E> synchronousQueueTo;
    private SynchronousQueue<E> synchronousQueueFrom;
    private CCThread processThread;
    private final String name;

    public CCSynchronizer(String name) {
        this.name = name;
    }

    public final void start() {
        if(synchronousQueueTo != null || synchronousQueueFrom != null || processThread != null) {
            stop();
        }
        synchronousQueueTo = new SynchronousQueue<E>();
        synchronousQueueFrom = new SynchronousQueue<E>();
        processThread = CHTask.manager.runThread(getConsumer(), false, 0l, 0l);
        processThread.setName(name);
        Loader.info(CHTask.cast + "[" + name + "] Synchronizer start");
    }

    public final void stop() {
        if(synchronousQueueTo != null) {
            synchronousQueueTo = null;
        }
        if(synchronousQueueFrom != null) {
            synchronousQueueFrom = null;
        }
        if(processThread != null) {
            CHTask.manager.removeThread(processThread);
            processThread = null;
        }
    }

    public final E process(E object) {
        try {
            synchronousQueueTo.put(object);
            return synchronousQueueFrom.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return object;
    }

    public final E takeTo() {
        try {
            return synchronousQueueTo.take();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public final void putFrom(E object) {
        try {
            synchronousQueueFrom.put(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract Runnable getConsumer();
}