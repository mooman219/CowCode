package com.gmail.mooman219.module.login;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.module.login.listener.ListenerData;
import com.gmail.mooman219.module.login.listener.ListenerPlayer;

public class CCLogin implements CowModule{
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Login] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerData listenerData;
    public ListenerPlayer listenerPlayer;

    public CCLogin(Loader head){
        plugin = head;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Login";
    }

    @Override
    public void onEnable(){
        listenerData = plugin.addListener(new ListenerData());
        listenerPlayer = plugin.addListener(new ListenerPlayer());
    }

    @Override
    public void onDisable(){}

    @Override
    public void loadCommands() {}

    public class Messages {
        public final Bulletin SHUTDOWN = new Bulletin(Chat.GRAY, "The server will be " + Chat.DARK_GREEN + "restarting. Kicking to save data.", Chat.formatPassive);
    }

    public class Formats {
        public final Bulletin LOGINDELAY = new Bulletin(Chat.GRAY + "Please wait another " + Chat.RED + "{0}" + Chat.GRAY + " before joining" + Chat.DARK_GRAY + ".");
    }
}
