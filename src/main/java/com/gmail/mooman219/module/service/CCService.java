package com.gmail.mooman219.module.service;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.service.command.Memory;
import com.gmail.mooman219.module.service.command.Test;
import com.gmail.mooman219.module.service.command.Whois;
import com.gmail.mooman219.module.service.listener.ListenerData;
import com.gmail.mooman219.module.service.listener.MessingAround;

public class CCService implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Services] ";

    public ListenerData listenerData;
    public MessingAround messingAround;

    public CCService(Loader head) {
        plugin = head;
    }

    @Override
    public void onEnable(){
        listenerData = new ListenerData();
        messingAround = new MessingAround();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerData, plugin);
        pm.registerEvents(messingAround, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.getCommand("whois").setExecutor(new Whois());
        plugin.getCommand("test").setExecutor(new Test());
        plugin.getCommand("memory").setExecutor(new Memory());
    }
}
