package com.gmail.mooman219.module.chat;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.team.LoneBoard;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.layout.CowModule;
import com.gmail.mooman219.layout.ModuleType;
import com.gmail.mooman219.module.chat.command.Global;
import com.gmail.mooman219.module.chat.command.Message;
import com.gmail.mooman219.module.chat.command.Mute;
import com.gmail.mooman219.module.chat.command.Reply;
import com.gmail.mooman219.module.chat.command.SetChatRange;
import com.gmail.mooman219.module.chat.command.SetOverheadPrefix;
import com.gmail.mooman219.module.chat.command.SetOverheadSuffix;
import com.gmail.mooman219.module.chat.listener.ListenerChat;
import com.gmail.mooman219.module.chat.listener.ListenerData;
import com.gmail.mooman219.module.chat.listener.ListenerPlayer;
import com.gmail.mooman219.module.chat.store.ConfigChat;

public class CCChat extends CowModule {
    private static final ModuleType type = ModuleType.CHAT;
    public static Messages MSG;
    public static Formats FRM;

    public final static LoneBoard loneBoard = new LoneBoard();;
    public ConfigChat configChat;

    public CCChat(){
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
        configChat = new ConfigChat();
        plugin.addListener(new ListenerChat());
        plugin.addListener(new ListenerPlayer());
        plugin.addListener(new ListenerData());
    }

    @Override
    public void loadCommands(Loader plugin) {
        plugin.addCommand(new Global());
        plugin.addCommand(new Message());
        plugin.addCommand(new Mute());
        plugin.addCommand(new Reply());
        plugin.addCommand(new SetChatRange(this));
        plugin.addCommand(new SetOverheadPrefix());
        plugin.addCommand(new SetOverheadSuffix());
    }

    public class Messages {
        public final Bulletin MESSAGE_EMPTY = new Bulletin(Chat.msgError, "Please specify a message.", Chat.formatError);
        public final Bulletin MESSAGE_SELF = new Bulletin(Chat.msgError, "You should stop talking to yourself.", Chat.formatError);
        public final Bulletin MESSAGE_LOST = new Bulletin(Chat.msgError, "No suitable player to respond to.", Chat.formatError);
        public final Bulletin NOHEAR = new Bulletin(Chat.msgWarn, "No one can hear you. Try adding \"!\" before your message.", Chat.formatWarn);
    }

    public class Formats {
        public final Bulletin DEATH = new Bulletin(Chat.msgPassive, "{0} has died.", Chat.formatPassive);
        public final Bulletin GLOBAL_DELAY = new Bulletin(Chat.msgError, "Wait {0} before next message.", Chat.formatError);
        public final Bulletin KICK = new Bulletin(Chat.msgPassive, "{0} has been kicked.", Chat.formatPassive);
        public final Bulletin LOGIN = new Bulletin(Chat.msgPassive, "{0} has logged in.", Chat.formatPassive);
        public final Bulletin MESSAGE_EXIST = new Bulletin(Chat.msgError, "\"{0}\" does not exist.", Chat.formatError);
        public final Bulletin MUTED_SENDER = new Bulletin(Chat.msgWarn, "{0} has been muted for {1}.", Chat.formatWarn);
        public final Bulletin MUTED_TARGET = new Bulletin(Chat.msgError, "You are currently muted for {0}.", Chat.formatError);
        public final Bulletin PLAYER_NOT_FOUND = new Bulletin(Chat.msgWarn, "Could not find {0}.", Chat.formatWarn);
        public final Bulletin QUIT = new Bulletin(Chat.msgPassive, "{0} has logged out.", Chat.formatPassive);
        public final Bulletin SETOVERHEAD = new Bulletin(Chat.msgInfo, "Your overhead name is now \"{0}\".", Chat.formatInfo);
        public final Bulletin SETRANGE = new Bulletin(Chat.msgInfo, "Chat range updated from [{0}] to [{1}]!", Chat.formatInfo);
    }
}
