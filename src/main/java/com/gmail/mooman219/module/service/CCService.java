package com.gmail.mooman219.module.service;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.service.command.LoadConfig;
import com.gmail.mooman219.module.service.command.Memory;
import com.gmail.mooman219.module.service.command.Test;
import com.gmail.mooman219.module.service.command.Whois;
import com.gmail.mooman219.module.service.listener.ListenerPlayer;
import com.gmail.mooman219.module.service.listener.ListenerEntity;
import com.gmail.mooman219.module.service.listener.MessingAround;

public class CCService extends CowModule {
    private static final ModuleType type = ModuleType.SERVICE;
    public static Messages MSG;
    public static Formats FRM;

    public CCService() {
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
        plugin.addListener(new ListenerPlayer());
        plugin.addListener(new ListenerEntity());
        plugin.addListener(new MessingAround());
    }

    @Override
    public void loadCommands(Loader plugin) {
        plugin.addCommand(new Whois());
        plugin.addCommand(new Test());
        plugin.addCommand(new Memory());
        plugin.addCommand(new LoadConfig());
    }

    public class Messages {
        public final Bulletin DATALOAD = new Bulletin(Chat.msgPassive, "Your data has been loaded successfully.", Chat.formatPassive);
        public final Bulletin CONFIGRELOAD = new Bulletin(Chat.msgInfo, "The config has been reloaded.", Chat.formatInfo);
        public final Bulletin DATAERROR = new Bulletin(Chat.RED, "An error has occured while attempting to access your data.\nYou may want to logout and log back in or try a different server to avoid losing information.", Chat.formatError);
        public final Bulletin LOGINERROR = new Bulletin(Chat.RED, "This server is unable to handle your login request currently.\nPlease try a different server.", Chat.formatError);
        public final Bulletin USERNAMEFAIL = new Bulletin(Chat.RED, "Unable to verify your username, please try restarting your client.\nThe login servers may be down.", Chat.formatError);
    }

    public class Formats {
        public final Bulletin WHOISERROR = new Bulletin(Chat.msgError, "\"{0}\" does not exist.", Chat.formatError);
    }
}
