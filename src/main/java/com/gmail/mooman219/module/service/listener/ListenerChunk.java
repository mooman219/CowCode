package com.gmail.mooman219.module.service.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;

import com.gmail.mooman219.bull.CDChunk;

public class ListenerChunk implements Listener {
    @EventHandler()
    public void onChunkLoad(ChunkLoadEvent event) {
    }

    @EventHandler()
    public void onChunkUnload(ChunkUnloadEvent event) {
        CDChunk.unload(event.getChunk());
    }
}
