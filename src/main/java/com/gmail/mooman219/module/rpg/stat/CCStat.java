package com.gmail.mooman219.module.rpg.stat;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;

public class CCStat implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][RPG Stat] ";
    public static Messages MSG;
    public static Formats FRM;

    public CCStat(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public void onEnable(){
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
