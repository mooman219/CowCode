package com.gmail.mooman219.module.mineral.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.mooman219.module.mineral.MineralManager;
import com.gmail.mooman219.module.mineral.store.BasicMineral;

public class ListenerBlock implements Listener{
    @EventHandler(ignoreCancelled = false)
    public void onBreak(BlockBreakEvent event) {
        BasicMineral mineral = MineralManager.getMineral(event.getBlock().getLocation());
        if(mineral != null) {
            event.setCancelled(true);
            if(event.getBlock().getType() == mineral.getType()) {
                MineralManager.mine(mineral);
            }
        }
    }
}
