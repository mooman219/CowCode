package com.gmail.mooman219.module.world.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;

import com.gmail.mooman219.module.world.store.ConfigWorld;

public class ListenerBlock implements Listener {
    @EventHandler()
    public void onBurn(BlockBurnEvent event) {
        if(ConfigWorld.getData().disableBlockBurn) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onFade(BlockFadeEvent event) {
        if(ConfigWorld.getData().disableBlockFade) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onForm(BlockFormEvent event) {
        if(ConfigWorld.getData().disableBlockForm) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onFromTo(BlockFromToEvent event) {
        if(ConfigWorld.getData().disableBlockFromTo) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onGrow(BlockGrowEvent event) {
        if(ConfigWorld.getData().disableBlockGrow) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onSpread(BlockSpreadEvent event) {
        if(ConfigWorld.getData().disableBlockSpread) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onLeavesDecay(LeavesDecayEvent event) {
        if(ConfigWorld.getData().disableLeafDecay) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onBreak(BlockBreakEvent event) {
        if(ConfigWorld.getData().disableBuilding) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onPlace(BlockPlaceEvent event) {
        if(ConfigWorld.getData().disableBuilding) {
            event.setCancelled(true);
        }
    }
}