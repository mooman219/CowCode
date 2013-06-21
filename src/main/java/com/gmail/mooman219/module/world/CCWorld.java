package com.gmail.mooman219.module.world;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.world.listener.ListenerBlock;

public class CCWorld implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][World] ";

    public ListenerBlock listenerBlock;

    public CCWorld(Loader head) {
        plugin = head;
    }
    
    @Override
    public String getName() {
        return "World"; 
    }

    @Override
    public void onEnable(){
        listenerBlock = new ListenerBlock();

        plugin.addListener(listenerBlock);
    }

    @Override
    public void onDisable() {}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}
}
