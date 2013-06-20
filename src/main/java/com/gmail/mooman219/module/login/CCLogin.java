package com.gmail.mooman219.module.login;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.module.login.listener.ListenerData;

public class CCLogin implements CowComponent{
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Login] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerData listenerPlayerData;

    public CCLogin(Loader head){
        plugin = head;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public void onEnable(){
        listenerPlayerData = new ListenerData();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerPlayerData, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {}

    public class Messages {
        public final Bulletin SHUTDOWN = new Bulletin(Chat.GRAY, "The server will be " + Chat.DARK_GREEN + "restarting. Kicking to save data.", Chat.formatPassive);
    }

    public class Formats {
        public final Bulletin LOGINDELAY = new Bulletin(Chat.GRAY + "Please wait another " + Chat.RED + "{0}" + Chat.GRAY + " before joining" + Chat.DARK_GRAY + ".");
    }
}
