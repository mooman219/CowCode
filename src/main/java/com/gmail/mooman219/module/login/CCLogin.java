package com.gmail.mooman219.module.login;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.Module;
import com.gmail.mooman219.module.login.listener.ListenerData;

public class CCLogin implements Module{
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Login] ";

    public ListenerData listenerPlayerData;

    public CCLogin(Loader head){
        plugin = head;
    }

    public void onEnable(){
        listenerPlayerData = new ListenerData();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayerData, plugin);

        Loader.info(cast + "Enabled");
    }

    public void onDisable(){
        Loader.info(cast + "Disabled");
    }
    
    public void registerConfigurationSerialization() {}

    public void loadCommands() {}
}
