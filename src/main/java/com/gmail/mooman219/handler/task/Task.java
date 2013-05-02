package com.gmail.mooman219.handler.task;

public class Task implements Runnable{
    public final Runnable slave;
    public final boolean hasDelay;
    public final boolean hasPeriod;
    public final boolean isInstant;
    public final long delay;
    public final long period;
    private boolean stop = false;
    private boolean isRunning = true;

    public Task(Runnable task) {
        this(task, -1l, -1l);
    }

    public Task(Runnable task, long delay) {
        this(task, delay, -1l);
    }

    public Task(Runnable task, long delay, long period) {
        this.slave = task;
        if(delay <= 0) {
            this.hasDelay = false;
            this.delay = 0l;
        } else {
            this.hasDelay = true;
            this.delay = delay;
        }
        if(period == 0) {
            this.hasPeriod = false;
            this.isInstant = true;
            this.period = 0l;
        } else if(period < 0) {
            this.hasPeriod = false;
            this.isInstant = false;
            this.period = 0l;
        } else {
            this.hasPeriod = true;
            this.isInstant = false;
            this.period = period;
        }
    }

    @Override
    public void run() {
        if(hasDelay) {
            rest(delay);
        }
        if(isInstant) {
            while(!stop && !CHTask.halt) {
                if(runTask()) {
                    break;
                }
            }
        } else if(hasPeriod) {
            while(!stop && !CHTask.halt) {
                if(runTask()) {
                    break;
                }
                rest(period);
            }            
        } else {
            runTask();
        }
        isRunning = false;
    }

    public void rest(long time) {
        try {
            Thread.sleep(time);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean runTask() {
        try {
            slave.run();
        } catch(Exception e) {
            e.printStackTrace();
            return true;
        }
        return false;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void stop() {
        this.stop = true;
    }
}
