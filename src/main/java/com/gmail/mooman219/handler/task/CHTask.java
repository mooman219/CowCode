package com.gmail.mooman219.handler.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.layout.CowHandler;

public class CHTask implements CowHandler {
    private Loader plugin;

    public final static String cast = "[Task] ";

    public static Manager manager;
    private ScheduledExecutorService asyncPool;
    private ExecutorService databasePool;

    public CHTask(Loader plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "Task";
    }

    @Override
    public void onEnable() {
        manager = new Manager();
        Loader.info(cast + "Starting plugin threads");
        asyncPool = Executors.newScheduledThreadPool(ConfigGlobal.handler.task.pluginThreads);
        databasePool = Executors.newFixedThreadPool(ConfigGlobal.handler.task.databaseThreads);
        Loader.info(cast + "Starting second clocks");

        manager.runPlugin(new Runnable() {
            @Override
            public void run() {
                CEventFactory.callTickSecondAsyncEvent();
            }
        }, 1000, 1000);

        manager.runBukkit(new Runnable() {
            @Override
            public void run() {
                CEventFactory.callTickSecondSyncEvent();
            }
        }, 20, 20);
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Stopping all threads");
        asyncPool.shutdown();
        databasePool.shutdown();
        try {
            asyncPool.awaitTermination(ConfigGlobal.handler.task.timeoutDelay, TimeUnit.SECONDS);
            databasePool.awaitTermination(ConfigGlobal.handler.task.timeoutDelay, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Manager {
        public BukkitTask runBukkit(Runnable runnable) {
            return Bukkit.getScheduler().runTask(plugin, runnable);
        }

        public BukkitTask runBukkit(Runnable runnable, long delay) {
            return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        }

        public BukkitTask runBukkit(Runnable runnable, long delay, long period) {
            return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
        }

        public <T> Future<T> runPlugin(Callable<T> callable) {
            return asyncPool.submit(callable);
        }

        public Future<?> runPlugin(Runnable runnable) {
            return asyncPool.submit(runnable);
        }

        public Future<?> runPlugin(Runnable runnable, long delay) {
            return asyncPool.schedule(runnable, delay, TimeUnit.MILLISECONDS);
        }

        public Future<?> runPlugin(Runnable runnable, long delay, long period) {
            return asyncPool.scheduleAtFixedRate(runnable, delay, period, TimeUnit.MILLISECONDS);
        }

        public Future<?> runDatabase(Runnable runnable) {
            return databasePool.submit(runnable);
        }
    }
}
