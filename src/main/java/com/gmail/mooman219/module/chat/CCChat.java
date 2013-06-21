package com.gmail.mooman219.module.chat;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
import com.gmail.mooman219.frame.text.Bulletin;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.chat.command.Global;
import com.gmail.mooman219.module.chat.command.Message;
import com.gmail.mooman219.module.chat.command.Reply;
import com.gmail.mooman219.module.chat.command.SetChatRange;
import com.gmail.mooman219.module.chat.command.SetOverheadName;
import com.gmail.mooman219.module.chat.listener.ListenerChat;
import com.gmail.mooman219.module.chat.listener.ListenerData;
import com.gmail.mooman219.module.chat.listener.ListenerPlayer;

public class CCChat implements CowComponent {
    public final Loader plugin;

    public final static String directory = "plugins/CowCraft/";
    public final static String cast = "[CC][Chat] ";
    public static Messages MSG;
    public static Formats FRM;

    public ListenerChat listenerChat;
    public ListenerPlayer listenerPlayer;
    public ListenerData listenerData;

    public CCChat(Loader plugin){
        this.plugin = plugin;
        MSG = new Messages();
        FRM = new Formats();
    }

    @Override
    public String getName() {
        return "Chat"; 
    }

    @Override
    public void onEnable(){
        listenerChat = new ListenerChat();
        listenerPlayer = new ListenerPlayer();
        listenerData = new ListenerData();

        plugin.addListener(listenerChat);
        plugin.addListener(listenerPlayer);
        plugin.addListener(listenerData);
    }

    @Override
    public void onDisable(){}

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.addCommand(new Global());
        plugin.addCommand(new Message());
        plugin.addCommand(new Reply());
        plugin.addCommand(new SetChatRange());
        plugin.addCommand(new SetOverheadName());
    }

    public class Messages {
        public final Bulletin MESSAGE_EMPTY = new Bulletin(Chat.msgError, "Please specify a message.", Chat.formatError);
        public final Bulletin MESSAGE_SELF = new Bulletin(Chat.msgError, "You should stop talking to yourself.", Chat.formatError);
        public final Bulletin MESSAGE_LOST = new Bulletin(Chat.msgError, "No suitable player to respond to.", Chat.formatError);
        public final Bulletin NOHEAR = new Bulletin(Chat.msgWarn, "No one can hear you. Try adding \"!\" before your message.", Chat.formatWarn);
    }

    public class Formats {
        public final Bulletin SETRANGE = new Bulletin(Chat.msgInfo, "Chat range updated from [{0}] to [{1}]!", Chat.formatInfo);
        public final Bulletin MUTED = new Bulletin(Chat.msgError, "You're currently muted for {0}.", Chat.formatError);
        public final Bulletin MESSAGE_EXIST = new Bulletin(Chat.msgError, "\"{0}\" does not exist.", Chat.formatError);
        public final Bulletin GLOBAL_DELAY = new Bulletin(Chat.msgError, "Wait {0} before next message.", Chat.formatError);
        public final Bulletin LOGIN = new Bulletin(Chat.msgPassive, "{0} has logged in.", Chat.formatPassive);
        public final Bulletin KICK = new Bulletin(Chat.msgPassive, "{0} has been kicked.", Chat.formatPassive);
        public final Bulletin QUIT = new Bulletin(Chat.msgPassive, "{0} has logged out.", Chat.formatPassive);
        public final Bulletin SETOVERHEAD = new Bulletin(Chat.msgInfo, "Your overhead name is now \"{0}\".", Chat.formatInfo);
    }
}
