package com.gmail.mooman219.module.world;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.world.listener.ListenerBlock;

public class CCWorld implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][World] ";

    public ListenerBlock listenerBlock;

    public CCWorld(Loader head) {
        plugin = head;
    }

    @Override
    public void onEnable(){
        listenerBlock = new ListenerBlock();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerBlock, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}
}
