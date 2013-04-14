package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.module.region.store.PLRegion;

public class ListenerData implements Listener{
    @EventHandler()
    public void onCreate(DataCreateEvent event) {
        event.getPlayer().getLive().set(new PLRegion());
    }
}
