package com.gmail.mooman219.module.world.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockGrowEvent;
import org.bukkit.event.block.BlockSpreadEvent;
import org.bukkit.event.block.LeavesDecayEvent;
import org.bukkit.event.weather.LightningStrikeEvent;

import com.gmail.mooman219.handler.config.ConfigGlobal;

public class ListenerBlock implements Listener {
    @EventHandler()
    public void onBurn(BlockBurnEvent event) {
        if(ConfigGlobal.module.world.disableBlockBurn) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onFade(BlockFadeEvent event) {
        if(ConfigGlobal.module.world.disableBlockFade) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onForm(BlockFormEvent event) {
        if(ConfigGlobal.module.world.disableBlockForm) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onFromTo(BlockFromToEvent event) {
        if(ConfigGlobal.module.world.disableBlockFromTo) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onGrow(BlockGrowEvent event) {
        if(ConfigGlobal.module.world.disableBlockGrow) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onSpread(BlockSpreadEvent event) {
        if(ConfigGlobal.module.world.disableBlockSpread) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onLeavesDecay(LeavesDecayEvent event) {
        if(ConfigGlobal.module.world.disableLeafDecay) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onLightningStrike(LightningStrikeEvent event) {
        if(ConfigGlobal.module.world.disableLightningStrike) {
            event.setCancelled(true);
        }
    }
}