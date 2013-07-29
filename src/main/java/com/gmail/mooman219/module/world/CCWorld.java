package com.gmail.mooman219.module.world;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.world.command.ToggleWorldSaving;
import com.gmail.mooman219.module.world.listener.ListenerBlock;
import com.gmail.mooman219.module.world.listener.ListenerWorld;

public class CCWorld extends CowModule {
    private static final ModuleType type = ModuleType.WORLD;
    public static Messages MSG;
    public static Formats FRM;

    public ListenerBlock listenerBlock;
    public ListenerWorld listenerWorld;

    public CCWorld(Loader plugin) {
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
        listenerBlock = getPlugin().addListener(new ListenerBlock());
        listenerWorld = getPlugin().addListener(new ListenerWorld());
    }

    @Override
    public void loadCommands() {
        getPlugin().addCommand(new ToggleWorldSaving());
    }

    public class Messages {}

    public class Formats {
        public final Bulletin WORLDSAVETOGGLE = new Bulletin(Chat.msgInfo, "World saving is currently [{0}].", Chat.formatInfo);
    }
}
