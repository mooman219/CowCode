package com.gmail.mooman219.module.world;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.world.listener.ListenerBlock;
import com.gmail.mooman219.module.world.listener.ListenerWorld;

public class CCWorld implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[World] ";

    public ListenerBlock listenerBlock;
    public ListenerWorld listenerWorld;

    public CCWorld(Loader head) {
        plugin = head;
    }

    @Override
    public String getName() {
        return "World";
    }

    @Override
    public void onEnable(){
        listenerBlock = plugin.addListener(new ListenerBlock());
        listenerWorld = plugin.addListener(new ListenerWorld());
    }

    @Override
    public void onDisable() {}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}
}
