package com.gmail.mooman219.module.rpg.item;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.module.rpg.item.listener.ListenerPlayer;

public class CCItem implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][RPG Item] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerPlayer listenerPlayer;

    public CCItem(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public void onEnable(){
        listenerPlayer = new ListenerPlayer();

        plugin.addListener(listenerPlayer);

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

    public class Messages {}

    public class Formats {}
}
