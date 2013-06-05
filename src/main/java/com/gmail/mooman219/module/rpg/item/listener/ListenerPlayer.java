package com.gmail.mooman219.module.rpg.item.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.module.rpg.item.Hotbar;

public class ListenerPlayer implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        Hotbar.apply(playerData);
    }
    
    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if(event.getItemDrop().getItemStack().getTypeId() == 118) {
            event.setCancelled(true);
        }
    }
}
