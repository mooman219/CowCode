package com.gmail.mooman219.module.graveyard.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.graveyard.CMGraveyard;
import com.gmail.mooman219.module.graveyard.GraveyardManager;
import com.gmail.mooman219.module.graveyard.store.CSGraveyard;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onRespawn(PlayerRespawnEvent event){
        CSGraveyard spawn = GraveyardManager.getClosestGraveyard(event.getPlayer().getLocation());
        if(spawn != null) {
            event.setRespawnLocation(spawn.getLocation());
            TextHelper.message(event.getPlayer(), CMGraveyard.M_RESPAWN);
        }
    }
}
