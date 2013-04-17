package com.gmail.mooman219.handler.task;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.task.task.AsyncTaskQueue;
import com.gmail.mooman219.handler.task.task.SyncTaskQueue;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.handler.task.type.CCThread;

public class TaskManager {
    private Loader plugin = null;
    private ArrayList<CCThread> liveThreads = null;
    private AsyncTaskQueue pluginAsyncThread = null;
    private SyncTaskQueue pluginSyncThread = null;

    public void start(Loader plugin) {
        if(this.liveThreads != null || this.plugin != null || this.pluginAsyncThread != null || this.pluginSyncThread != null) {
            stop();
        }
        this.pluginAsyncThread = new AsyncTaskQueue();
        this.pluginSyncThread = new SyncTaskQueue();
        this.liveThreads = new ArrayList<CCThread>();
        this.plugin = plugin;
        pluginAsyncThread.start();
        pluginSyncThread.start();
    }

    public void stop() {
        if(this.plugin != null) {
            Bukkit.getScheduler().cancelTasks(plugin);
            plugin = null;
        }
        if(liveThreads != null) {
            for(CCThread thread : liveThreads) {
                if(thread != null) {
                    thread.stop = true;
                    thread.isRemoved = true;
                }
            }
            liveThreads.clear();
            liveThreads = null;
        }
        if(pluginAsyncThread != null) {
            pluginAsyncThread.stop();
            pluginAsyncThread = null;
        }
        if(pluginSyncThread != null) {
            pluginSyncThread.stop();
            pluginSyncThread = null;
        }
    }

    public BukkitTask runTask(Runnable task) {
        return Bukkit.getScheduler().runTask(plugin, task);
    }

    // Time is in ticks (20 mills = 1 second)
    public BukkitTask runTaskLater(Runnable task, long delay) {
        return Bukkit.getScheduler().runTaskLater(plugin, task, delay);
    }

    // Time is in ticks (20 mills = 1 second)
    public BukkitTask runTaskTimer(Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period);
    }

    public BukkitTask runAsyncTask(Runnable task) {
        return Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    // Time is in ticks (20 mills = 1 second)
    public BukkitTask runAsyncTaskLater(Runnable task, long delay) {
        return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
    }

    // Time is in ticks (20 mills = 1 second)
    public BukkitTask runAsyncTaskTimer(Runnable task, long delay, long period) {
        return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period);
    }

    public CCThread runThread(CCThread thread) {
        liveThreads.add(thread);
        thread.start();
        return thread;
    }

    // Time is in mills (1000 mills = 1 second)
    public CCThread runThread(Runnable runnable, boolean runOnce, long delay, long period) {
        return runThread(new CCThread(runnable, runOnce, delay, period));
    }

    // Runs a task on the plugin's async thread
    // Use if you don't care if the task finishes
    public void runAsyncPluginTask(CCTask task) {
        pluginAsyncThread.put(task);
    }

    // Runs a task on the plugin's sync thread
    // Use if you want to wait until the task finishes
    public <T extends CCTask> T runSyncPluginTask(T task) {
        return (T) pluginSyncThread.process(task);
    }

    public void removeThread(CCThread thread) {
        thread.stop = true;
        if(!thread.isRemoved) {
            thread.isRemoved = true;
            liveThreads.remove(thread);
        }
        Loader.info(CHTask.cast + "Finished [" + thread.getName() + "]");
    }
}
