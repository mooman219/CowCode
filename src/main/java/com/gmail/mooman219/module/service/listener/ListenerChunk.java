package com.gmail.mooman219.module.service.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.gmail.mooman219.bull.CDChunk;

public class ListenerChunk implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onUnload(ChunkUnloadEvent event) {
        CDChunk.unload(event.getChunk());
    }
}
