package com.gmail.mooman219.module.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.event.DataCreateEvent;

public class ListenerData implements Listener{
    @EventHandler()
    public void onCreate(DataCreateEvent event) {
        CDPlayer player = event.getPlayer();
        player.setDisplayName(player.serviceData.rank.color + player.username);
        player.setOverheadName(player.serviceData.rank.color + player.getName());
    }
}
