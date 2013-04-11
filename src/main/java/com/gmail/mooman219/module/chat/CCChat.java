package com.gmail.mooman219.module.chat;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.Module;
import com.gmail.mooman219.module.chat.command.Global;
import com.gmail.mooman219.module.chat.command.Message;
import com.gmail.mooman219.module.chat.command.Reply;
import com.gmail.mooman219.module.chat.command.SetChatRange;
import com.gmail.mooman219.module.chat.listener.ListenerChat;
import com.gmail.mooman219.module.chat.listener.ListenerData;
import com.gmail.mooman219.module.chat.listener.ListenerPlayer;
import com.gmail.mooman219.module.chat.queue.ChatQueue;

public class CCChat implements Module {
    public final Loader plugin;

    public static ChatQueue queueChat;

    public final static String directord = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Chat] ";

    public ListenerChat listenerChat;
    public ListenerPlayer listenerPlayer;
    public ListenerData listenerData;

    public CCChat(Loader plugin){
        this.plugin = plugin;
    }

    public void onEnable(){
        Loader.info(cast + "Starting ChatQueue");
        queueChat = new ChatQueue();
        queueChat.start();
        listenerChat = new ListenerChat();
        listenerPlayer = new ListenerPlayer();
        listenerData = new ListenerData();
        
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerChat, plugin);
        pm.registerEvents(listenerPlayer, plugin);
        pm.registerEvents(listenerData, plugin);
        
        Loader.info(cast + "Enabled");
    }

    public void onDisable(){
        Loader.info(cast + "Stopping ChatQueue");
        queueChat.stop();
        Loader.info(cast + "Disabled");
    }
    
    public void registerConfigurationSerialization() {}

    public void loadCommands() {
        plugin.getCommand("global").setExecutor(new Global());
        plugin.getCommand("reply").setExecutor(new Reply());
        plugin.getCommand("message").setExecutor(new Message());
        plugin.getCommand("setchatrange").setExecutor(new SetChatRange());
    }
}
