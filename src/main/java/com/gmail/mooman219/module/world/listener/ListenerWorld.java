package com.gmail.mooman219.module.world.listener;

import org.bukkit.Difficulty;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.event.world.WorldInitEvent;

import com.gmail.mooman219.handler.config.ConfigGlobal;

public class ListenerWorld implements Listener {
    @EventHandler()
    public void onStructureGrow(StructureGrowEvent event) {
        if(ConfigGlobal.disableStructureGrow) {
            event.setCancelled(true);
        }
    }

    @EventHandler()
    public void onInit(WorldInitEvent event) {
        if(ConfigGlobal.disableWorldSaving) {            
            event.getWorld().setAutoSave(false);
        }
        event.getWorld().setDifficulty(Difficulty.HARD);
        event.getWorld().setGameRuleValue("keepInventory", "true");
        event.getWorld().setGameRuleValue("doDaylightCycle", "true");
        event.getWorld().setGameRuleValue("doFireTick", "false");
        event.getWorld().setGameRuleValue("doMobLoot", "false");
        event.getWorld().setGameRuleValue("doMobSpawning", "false");
        event.getWorld().setGameRuleValue("doTileDrops", "false");
        event.getWorld().setGameRuleValue("mobGriefing", "false");
    }
}
