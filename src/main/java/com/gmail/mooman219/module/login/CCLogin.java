package com.gmail.mooman219.module.login;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.login.listener.ListenerData;

public class CCLogin implements CowComponent{
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Login] ";

    public ListenerData listenerPlayerData;

    public CCLogin(Loader head){
        plugin = head;
    }

    @Override
    public void onEnable(){
        listenerPlayerData = new ListenerData();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayerData, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}
}
