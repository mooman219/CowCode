package com.gmail.mooman219.module.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.frame.PlayerHelper;
import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.module.chat.store.PLChat;

public class ListenerData implements Listener{
    @EventHandler()
    public void onCreate(DataCreateEvent event) {
        event.playerData.chat = new PLChat();
        PlayerHelper.setDisplayName(event.playerData.player, event.playerData.serviceData.rank.color + event.playerData.username);
    }
}
