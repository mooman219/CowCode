package com.gmail.mooman219.module.item.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.service.event.DataCreateEvent;

public class ListenerData implements Listener {
    @EventHandler()
    public void onCreation(DataCreateEvent event) {
        CDPlayer player = event.getPlayer();
        player.getPlayer().getInventory().setContents(player.item.getInventory());
    }
}