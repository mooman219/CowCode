package com.gmail.mooman219.vanilla;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.vanilla.command.Music;

public class CCVanilla implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Vanilla] ";
    public static Messages MSG;
    public static Formats FRM;

    public CCVanilla(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Vanilla";
    }

    @Override
    public void onEnable(){}

    @Override
    public void onDisable(){}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new Music());
    }

    public class Messages {}

    public class Formats {
        public final Bulletin MUSIC = new Bulletin(Chat.msgInfo, "Playing ID {0}!", Chat.formatInfo);
    }
}
