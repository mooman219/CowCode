package com.gmail.mooman219.module.world;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.Module;
import com.gmail.mooman219.module.world.listener.ListenerBlock;

public class CCWorld implements Module {
    public final Loader plugin;
    
    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][World] ";

    public ListenerBlock listenerBlock;

    public CCWorld(Loader head) {
        plugin = head;
    }

    public void onEnable(){
        listenerBlock = new ListenerBlock();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerBlock, plugin);

        Loader.info(cast + "Enabled");
    }

    public void onDisable() {
        Loader.info(cast + "Disabled");
    }
    
    public void registerConfigurationSerialization() {}

    public void loadCommands() {}
}
