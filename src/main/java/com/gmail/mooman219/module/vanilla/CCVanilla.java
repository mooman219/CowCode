package com.gmail.mooman219.module.vanilla;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.vanilla.command.Gamemode;
import com.gmail.mooman219.module.vanilla.command.Music;
import com.gmail.mooman219.module.vanilla.command.Shriek;

public class CCVanilla extends CowModule {
    private static final ModuleType type = ModuleType.VANILLA;
    public static Messages MSG;
    public static Formats FRM;

    public CCVanilla(){
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
    public void loadCommands(Loader plugin) {
        plugin.addCommand(new Music());
        plugin.addCommand(new Shriek());
        plugin.addCommand(new Gamemode());
    }

    public class Messages {
        public final Bulletin SHRIEK = new Bulletin(Chat.msgInfo, "You scream at the top of your lungs!", Chat.formatInfo);
        public final Bulletin SUICIDE = new Bulletin(Chat.msgError, "You commit suicide.", Chat.formatError);
        public final Bulletin SUICIDE_LOL = new Bulletin(Chat.msgError, "A very fast mutation begins, cellular growth quickly grows skin all over the open orifices on your face. Helvetica Scenario.", Chat.formatError);
        public final Bulletin HEAL = new Bulletin(Chat.msgInfo, "You have been healed.", Chat.formatInfo);
    }

    public class Formats {
        public final Bulletin MUSIC = new Bulletin(Chat.msgInfo, "Playing ID {0}!", Chat.formatInfo);
        public final Bulletin GAMEMODE = new Bulletin(Chat.msgInfo, "Your gamemode is now {0}", Chat.formatInfo);
    }
}
