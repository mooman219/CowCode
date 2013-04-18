package com.gmail.mooman219.module.service;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.Module;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.databse.task.UploadTask;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.login.CMLogin;
import com.gmail.mooman219.module.service.command.Memory;
import com.gmail.mooman219.module.service.command.Test;
import com.gmail.mooman219.module.service.command.Whois;
import com.gmail.mooman219.module.service.listener.ListenerData;
import com.gmail.mooman219.module.service.listener.MessingAround;

public class CCService implements Module {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Services] ";

    public ListenerData listenerData;
    public MessingAround messingAround;

    public CCService(Loader head) {
        plugin = head;
    }

    public void onEnable(){
        listenerData = new ListenerData();
        messingAround = new MessingAround();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerData, plugin);
        pm.registerEvents(messingAround, plugin);

        Loader.info(cast + "Enabled");
    }

    public void onDisable() {
        Loader.info(cast + "Removing players");
        for(Player player : Bukkit.getOnlinePlayers()) {
            CDPlayer playerData = CDPlayer.get(player);
            CHTask.manager.runAsyncPluginTask(UploadTask.get(UploadType.NORMAL, playerData));
            player.kickPlayer(CMLogin.M_SHUTDOWN);
            Loader.info(CCService.cast + "[STOP] (" + Bukkit.getOnlinePlayers().length + ") normal: " + playerData.username);
        }
        Loader.info(cast + "Disabled");
    }
    
    public void registerConfigurationSerialization() {}

    public void loadCommands() {
        plugin.getCommand("whois").setExecutor(new Whois());
        plugin.getCommand("test").setExecutor(new Test());
        plugin.getCommand("memory").setExecutor(new Memory());
    }
}
