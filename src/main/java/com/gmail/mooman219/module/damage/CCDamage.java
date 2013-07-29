package com.gmail.mooman219.module.damage;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.scoreboard.HealthBoard;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.damage.listener.ListenerEntity;
import com.gmail.mooman219.module.damage.listener.ListenerPlayer;

public class CCDamage extends CowModule {
    private static final ModuleType type = ModuleType.DAMAGE;
    public static Messages MSG;
    public static Formats FRM;

    public final static HealthBoard healthBoard = new HealthBoard("health", Chat.RED + "" + Chat.BOLD + "HP");
    public ListenerEntity listenerEntity;
    public ListenerPlayer listenerPlayer;

    public CCDamage(Loader plugin){
        super(plugin);
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public ModuleType getType() {
        return type;
    }

    public static String getName() {
        return type.getName();
    }

    public static String getCast() {
        return type.getCast();
    }

    public static String getDirectory() {
        return type.getDirectory();
    }

    @Override
    public void onEnable(){
        listenerEntity = getPlugin().addListener(new ListenerEntity());
        listenerPlayer = getPlugin().addListener(new ListenerPlayer());
    }

    public class Messages {}

    public class Formats {
        public final Bulletin BARHEALTH = new Bulletin(Chat.RED + "" + Chat.BOLD + "HP" + Chat.RED + " {0}");
    }
}
