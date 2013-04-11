package com.gmail.mooman219.module.mineral.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import com.gmail.mooman219.module.mineral.MineralManager;
import com.gmail.mooman219.module.mineral.serialize.CSMineral;

public class ListenerBlock implements Listener{
    @EventHandler(ignoreCancelled = false)
    public void onBreak(BlockBreakEvent event) {
        CSMineral mineralData = MineralManager.getMineral(event.getBlock().getLocation());
        if(mineralData != null && event.getBlock().getType() == mineralData.type) {
            mineralData.timeLeft = (int) (mineralData.respawnDelay + (4 * Math.random()));
            MineralManager.addActiveMineral(mineralData);
            event.setCancelled(false);
        }
    }
}
