package com.gmail.mooman219.module.graveyard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.module.graveyard.CCGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.FastGraveyard;

public class ListenerPlayer implements Listener{
    @EventHandler(priority = EventPriority.LOWEST)
    public void onRespawn(PlayerRespawnEvent event){
        FastGraveyard spawn = GraveyardManager.getClosestGraveyard(event.getPlayer().getLocation());
        if(spawn != null) {
            event.setRespawnLocation(spawn.toLocation());
            CCGraveyard.MSG.RESPAWN.send(event.getPlayer());
        }
    }
}
