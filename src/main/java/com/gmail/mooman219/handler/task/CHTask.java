package com.gmail.mooman219.handler.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import com.gmail.mooman219.core.CowHandler;
import com.gmail.mooman219.core.Loader;

public class CHTask implements CowHandler {
    private Loader plugin;
    
    public final static String cast = "[CC][H][Task] ";

    public static Manager manager;
    public boolean test = false;
    protected static boolean halt = false; 
    private ExecutorService asyncPool;
    private PluginSyncQueue syncQueue;

    public CHTask(Loader plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onEnable() {
        manager = new Manager();
        Loader.info(cast + "Starting plugin threads");
        asyncPool = Executors.newFixedThreadPool(5);
        syncQueue = new PluginSyncQueue();
        syncQueue.start();
        Loader.info(cast + "Starting second clocks");
        manager.runThread(new SecondClockAsync(), 1000l, 1000l);
        manager.runBukkit(new SecondClockSync(), false, 20, 20);
        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Stopping all threads");
        halt = true;
        asyncPool.shutdown();
        Loader.info(cast + "Disabled");
    }
    
    public class Manager {
        public BukkitTask runBukkit(Runnable runnable, boolean async) {
            if(async) {
                return Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);            
            }
            return Bukkit.getScheduler().runTask(plugin, runnable);
        }
        
        public BukkitTask runBukkit(Runnable runnable, boolean async, long delay) {
            if(async) {
                return Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, delay);            
            }
            return Bukkit.getScheduler().runTaskLater(plugin, runnable, delay);
        }
        
        public BukkitTask runBukkit(Runnable runnable, boolean async, long delay, long period) {
            if(async) {
                return Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, delay, period);            
            }
            return Bukkit.getScheduler().runTaskTimer(plugin, runnable, delay, period);
        }

        public Thread runThread(Runnable runnable) {
            return runThread(runnable, -1l, -1l);
        }
        
        public Thread runThread(Runnable runnable, long delay) {
            return runThread(runnable, delay, -1l);
        }
        
        public Thread runThread(Runnable runnable, long delay, long period) {
            Thread thread = new Thread(new Task(runnable, delay, period));
            thread.start();
            return thread;
        }
        
        public Runnable runPlugin(Runnable runnable, boolean async) {
            if(async) {
                asyncPool.submit(runnable);
                return null;
            }
            return syncQueue.put(runnable);            
        }
    }
}
