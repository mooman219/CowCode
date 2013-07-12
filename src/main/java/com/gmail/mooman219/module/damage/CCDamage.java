package com.gmail.mooman219.module.damage;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.damage.listener.ListenerEntity;

public class CCDamage implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Damage] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerEntity listenerEntity;

    public CCDamage(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Damage";
    }

    @Override
    public void onEnable(){
        listenerEntity = plugin.addListener(new ListenerEntity());
    }

    @Override
    public void onDisable(){}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}

    public class Messages {}

    public class Formats {}
}
