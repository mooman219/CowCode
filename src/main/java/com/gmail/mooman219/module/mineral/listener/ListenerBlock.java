package com.gmail.mooman219.module.mineral.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.module.mineral.store.Mineral;

public class ListenerBlock implements Listener{
    @EventHandler(ignoreCancelled = false)
    public void onBreak(BlockBreakEvent event) {
        Mineral mineral = CDChunk.get(event.getBlock()).getMineral(event.getBlock());
        if(mineral != null && event.getBlock().getType() == mineral.type) {
            mineral.mine(event.getBlock().getChunk());
        }
    }
}
