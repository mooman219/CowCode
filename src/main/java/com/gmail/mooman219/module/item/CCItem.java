package com.gmail.mooman219.module.item;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.module.item.command.ItemStats;
import com.gmail.mooman219.module.item.command.ResetInventory;
import com.gmail.mooman219.module.item.listener.ListenerData;
import com.gmail.mooman219.module.item.listener.ListenerInventory;
import com.gmail.mooman219.module.item.listener.ListenerPlayer;

public class CCItem implements CowModule {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Item] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerPlayer listenerPlayer;
    public ListenerData listenerData;
    public ListenerInventory listenerInventory;

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
        listenerPlayer = plugin.addListener(new ListenerPlayer());
        listenerData = plugin.addListener(new ListenerData());
        listenerInventory = plugin.addListener(new ListenerInventory());
    }

    @Override
    public void onDisable(){}

    @Override
    public void loadCommands() {
        plugin.addCommand(new ItemStats());
        plugin.addCommand(new ResetInventory());
    }

    public class Messages {
        public final Bulletin STATFAIL = new Bulletin(Chat.msgError, "You don't have an item in your hand!", Chat.formatError);
        public final Bulletin INVENTORY_RESET = new Bulletin(Chat.msgError, "Your inventory has be reset to its default state.", Chat.formatError);
    }

    public class Formats {}
}
