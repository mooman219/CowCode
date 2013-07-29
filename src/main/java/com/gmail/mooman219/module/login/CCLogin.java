package com.gmail.mooman219.module.login;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.login.listener.ListenerData;
import com.gmail.mooman219.module.login.listener.ListenerPlayer;

public class CCLogin extends CowModule{
    private static final ModuleType type = ModuleType.LOGIN;
    public static Messages MSG;
    public static Formats FRM;

    public CCLogin() {
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
        plugin.addListener(new ListenerData());
        plugin.addListener(new ListenerPlayer());
    }

    public class Messages {
        public final Bulletin SHUTDOWN = new Bulletin(Chat.GRAY, "The server will be " + Chat.DARK_GREEN + "restarting. Kicking to save data.", Chat.formatPassive);
    }

    public class Formats {
        public final Bulletin LOGINDELAY = new Bulletin(Chat.GRAY + "Please wait another " + Chat.RED + "{0}" + Chat.GRAY + " before joining" + Chat.DARK_GRAY + ".");
    }
}
