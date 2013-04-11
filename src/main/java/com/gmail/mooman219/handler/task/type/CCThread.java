package com.gmail.mooman219.handler.task.type;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.task.CHTask;

public class CCThread extends Thread {
    public final Runnable runnableSlave;
    public final long delay;
    public final long period;
    public final boolean runOnce;
    public boolean stop;
    public boolean isRemoved;

    public CCThread(Runnable runnableSlave, boolean runOnce, long delay, long period) {
        this.runnableSlave = runnableSlave;
        this.delay = delay < 0 ? 0 : delay;
        this.period = period < 0 ? 0 : period;
        this.runOnce = runOnce;
        this.stop = false;
        this.isRemoved = false;
    }

    public final void run() {
        if(delay > 0) {
            rest(delay);
        }
        if(runOnce) {
            process();
        } else {
            while(!stop) {
                process();
                if(period > 0) {
                    rest(period);
                }
            }
        }
        CHTask.manager.removeThread(this);
    }

    public final void rest(Long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public final void process() {
        try {
            runnableSlave.run();
        } catch(Exception e) {
            e.printStackTrace();
            Loader.warning(CHTask.cast + this.getName() + " has experienced a crash.");
        }
    }
}
