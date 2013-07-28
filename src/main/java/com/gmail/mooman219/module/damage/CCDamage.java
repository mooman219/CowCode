package com.gmail.mooman219.module.damage;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.scoreboard.HealthBoard;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.module.damage.listener.ListenerEntity;
import com.gmail.mooman219.module.damage.listener.ListenerPlayer;

public class CCDamage implements CowModule {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Damage] ";
    public static Messages MSG;
    public static Formats FRM;

    public final static HealthBoard healthBoard = new HealthBoard("health", Chat.RED + "" + Chat.BOLD + "HP");
    public ListenerEntity listenerEntity;
    public ListenerPlayer listenerPlayer;

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
        listenerPlayer = plugin.addListener(new ListenerPlayer());
    }

    @Override
    public void onDisable(){}

    @Override
    public void loadCommands() {}

    public class Messages {}

    public class Formats {
        public final Bulletin BARHEALTH = new Bulletin(Chat.RED + "" + Chat.BOLD + "HP" + Chat.RED + " {0}");
    }
}
