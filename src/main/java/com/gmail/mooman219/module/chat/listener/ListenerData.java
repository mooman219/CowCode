package com.gmail.mooman219.module.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.module.CDPlayer;

public class ListenerData implements Listener{
    @EventHandler()
    public void onCreate(DataCreateEvent event) {
        CDPlayer player = event.getPlayer();
        player.setDisplayName(player.serviceData.rank.color + player.username);
    }
}
