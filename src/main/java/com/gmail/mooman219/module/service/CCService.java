package com.gmail.mooman219.module.service;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.service.command.Memory;
import com.gmail.mooman219.module.service.command.Test;
import com.gmail.mooman219.module.service.command.Whois;
import com.gmail.mooman219.module.service.listener.ListenerData;
import com.gmail.mooman219.module.service.listener.MessingAround;

public class CCService implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Services] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerData listenerData;
    public MessingAround messingAround;

    public CCService(Loader head) {
        plugin = head;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public void onEnable(){
        listenerData = new ListenerData();
        messingAround = new MessingAround();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerData, plugin);
        pm.registerEvents(messingAround, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable() {
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new Whois());
        plugin.addCommand(new Test());
        plugin.addCommand(new Memory());
    }
    
    public class Messages {
        public final Bulletin DATALOAD = new Bulletin(Chat.msgPassive, "Your data has been loaded successfully.", Chat.formatPassive);
        public final Bulletin LOGINERROR = new Bulletin(Chat.RED + "This server is unable to handle your login request currently, please try a different one.", Chat.formatError);
    }
    
    public class Formats {
        public final Bulletin WHOISERROR = new Bulletin(Chat.msgError, "\"{0}\" does not exist.", Chat.formatError);
    }
}
