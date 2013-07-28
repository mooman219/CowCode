package com.gmail.mooman219.module.vanilla;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.module.vanilla.command.Gamemode;
import com.gmail.mooman219.module.vanilla.command.Heal;
import com.gmail.mooman219.module.vanilla.command.Music;
import com.gmail.mooman219.module.vanilla.command.Shriek;
import com.gmail.mooman219.module.vanilla.command.Suicide;

public class CCVanilla implements CowModule {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[Vanilla] ";
    public static Messages MSG;
    public static Formats FRM;

    public CCVanilla(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Vanilla";
    }

    @Override
    public void onEnable(){}

    @Override
    public void onDisable(){}

    @Override
    public void loadCommands() {
        plugin.addCommand(new Music());
        plugin.addCommand(new Shriek());
        plugin.addCommand(new Gamemode());
        plugin.addCommand(new Suicide());
        plugin.addCommand(new Heal());
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
