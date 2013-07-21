package com.gmail.mooman219.module.world;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowComponent;
import com.gmail.mooman219.module.world.command.ToggleWorldSaving;
import com.gmail.mooman219.module.world.listener.ListenerBlock;
import com.gmail.mooman219.module.world.listener.ListenerWorld;

public class CCWorld implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[World] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerBlock listenerBlock;
    public ListenerWorld listenerWorld;

    public CCWorld(Loader head) {
        plugin = head;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "World";
    }

    @Override
    public void onEnable(){
        listenerBlock = plugin.addListener(new ListenerBlock());
        listenerWorld = plugin.addListener(new ListenerWorld());
    }

    @Override
    public void onDisable() {}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new ToggleWorldSaving());
    }

    public class Messages {}

    public class Formats {
        public final Bulletin WORLDSAVETOGGLE = new Bulletin(Chat.msgInfo, "World saving is currently [{0}].", Chat.formatInfo);
    }
}
