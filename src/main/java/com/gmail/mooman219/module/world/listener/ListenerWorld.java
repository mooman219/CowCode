package com.gmail.mooman219.module.world.listener;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.LightningStrikeEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;

import com.gmail.mooman219.handler.config.store.ConfigGlobal;

public class ListenerWorld implements Listener {
    @EventHandler()
    public void onLightningStrike(LightningStrikeEvent event) {
        if(ConfigGlobal.module.world.disableLightningStrike) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onStructureGrow(StructureGrowEvent event) {
        if(ConfigGlobal.module.world.disableStructureGrow) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onInit(WorldInitEvent event) {
        prepWorld(event.getWorld());
    }

    @EventHandler()
    public void onLoad(WorldLoadEvent event) {
        prepWorld(event.getWorld());
    }

    public void prepWorld(World world) {
        if(ConfigGlobal.module.world.disableWorldSaving) {
            world.setAutoSave(false);
        }
        world.setDifficulty(Difficulty.HARD);
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("doDaylightCycle", "true");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("doMobLoot", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doTileDrops", "false");
        world.setGameRuleValue("mobGriefing", "false");
    }
}
