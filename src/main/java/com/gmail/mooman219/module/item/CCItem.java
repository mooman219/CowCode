package com.gmail.mooman219.module.item;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.item.command.ItemStats;
import com.gmail.mooman219.module.item.listener.ListenerPlayer;

public class CCItem implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Item] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerPlayer listenerPlayer;

    public CCItem(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Item";
    }

    @Override
    public void onEnable(){
        listenerPlayer = new ListenerPlayer();

        plugin.addListener(listenerPlayer);
    }

    @Override
    public void onDisable(){}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new ItemStats());
    }

    public class Messages {
        public final Bulletin STATFAIL = new Bulletin(Chat.msgError, "You don't have an item in your hand!.", Chat.formatError);
    }

    public class Formats {}
}
