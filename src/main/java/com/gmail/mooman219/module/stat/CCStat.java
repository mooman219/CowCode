package com.gmail.mooman219.module.stat;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.layout.CowModule;

public class CCStat implements CowModule {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Stat] ";
    public static Messages MSG;
    public static Formats FRM;

    public CCStat(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Stat";
    }

    @Override
    public void onEnable(){}

    @Override
    public void onDisable(){}

    @Override
    public void loadCommands() {}

    public class Messages {}

    public class Formats {}
}
