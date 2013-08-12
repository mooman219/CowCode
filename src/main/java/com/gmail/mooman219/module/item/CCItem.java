package com.gmail.mooman219.module.item;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.item.command.ItemStats;
import com.gmail.mooman219.module.item.command.ResetInventory;
import com.gmail.mooman219.module.item.listener.ListenerButton;
import com.gmail.mooman219.module.item.listener.ListenerInventory;
import com.gmail.mooman219.module.item.listener.ListenerPlayer;

public class CCItem extends CowModule {
    private static final ModuleType type = ModuleType.ITEM;
    public static Messages MSG;
    public static Formats FRM;

    public CCItem() {
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
    public void onEnable(Loader plugin){
        plugin.addListener(new ListenerButton());
        plugin.addListener(new ListenerPlayer());
        plugin.addListener(new ListenerInventory());
    }

    @Override
    public void loadCommands(Loader plugin) {
        plugin.addCommand(new ItemStats());
        plugin.addCommand(new ResetInventory());
    }

    public class Messages {
        public final Bulletin STATFAIL = new Bulletin(Chat.msgError, "You don't have an item in your hand!", Chat.formatError);
        public final Bulletin INVENTORY_RESET = new Bulletin(Chat.msgError, "Your inventory has be reset to its default state.", Chat.formatError);
    }

    public class Formats {}
}
