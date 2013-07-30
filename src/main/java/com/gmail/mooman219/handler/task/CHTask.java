package com.gmail.mooman219.handler.task;

import java.util.concurrent.Callable;
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
import com.gmail.mooman219.layout.HandlerType;
import com.google.common.util.concurrent.ThreadFactoryBuilder;

public class CHTask extends CowHandler {
    private static final HandlerType type = HandlerType.TASK;
    public final Loader plugin;
    private static Manager manager;

    private ScheduledExecutorService asyncPool;

    public CHTask(Loader plugin) {
        this.plugin = plugin;
    }

    @Override
    public HandlerType getType() {
        return type;
    }

    public static String getName() {
        return type.getName();
    }

    public static String getCast() {
        return type.getCast();
    }

    public static String getDirectory() {
        return type.getDirectory();
    }

    public Loader getPlugin() {
        return plugin;
    }

    @Override
    public void onEnable() {
        manager = new Manager();
        Loader.info(getCast() + "Starting plugin threads");
        asyncPool = Executors.newScheduledThreadPool(ConfigGlobal.handler.task.pluginThreads, new ThreadFactoryBuilder().setNameFormat("Cow Plugin Thread - %1$d").build());
        Loader.info(getCast() + "Starting second clocks");

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

    public static Manager getManager() {
        return manager;
    }

    @Override
    public void onDisable() {
        Loader.info(getCast() + "Stopping all threads");
        asyncPool.shutdown();
        try {
            asyncPool.awaitTermination(ConfigGlobal.handler.task.timeoutDelay, TimeUnit.SECONDS);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }

    public class Manager {
        public BukkitTask runBukkit(Runnable runnable) {
            return Bukkit.getScheduler().runTask(getPlugin(), runnable);
        }

        public BukkitTask runBukkit(Runnable runnable, long delay) {
            return Bukkit.getScheduler().runTaskLater(getPlugin(), runnable, delay);
        }

        public BukkitTask runBukkit(Runnable runnable, long delay, long period) {
            return Bukkit.getScheduler().runTaskTimer(getPlugin(), runnable, delay, period);
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
    }
}
