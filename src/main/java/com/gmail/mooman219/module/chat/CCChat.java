package com.gmail.mooman219.module.chat;

import org.bukkit.plugin.PluginManager;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.core.CowComponent;
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

    public final static String directord = "plugins/CowCraft/";
    public final static String cast = "[CC][M][Chat] ";

    public ListenerChat listenerChat;
    public ListenerPlayer listenerPlayer;
    public ListenerData listenerData;

    public CCChat(Loader plugin){
        this.plugin = plugin;
    }

    @Override
    public void onEnable(){
        listenerChat = new ListenerChat();
        listenerPlayer = new ListenerPlayer();
        listenerData = new ListenerData();

        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(listenerChat, plugin);
        pm.registerEvents(listenerPlayer, plugin);
        pm.registerEvents(listenerData, plugin);

        Loader.info(cast + "Enabled");
    }

    @Override
    public void onDisable(){
        Loader.info(cast + "Disabled");
    }

    @Override
    public void registerConfigurationSerialization() {}

    @Override
    public void loadCommands() {
        plugin.getCommand("global").setExecutor(new Global());
        plugin.getCommand("reply").setExecutor(new Reply());
        plugin.getCommand("message").setExecutor(new Message());
        plugin.getCommand("setchatrange").setExecutor(new SetChatRange());
        plugin.getCommand("setoverheadname").setExecutor(new SetOverheadName());
    }
}
