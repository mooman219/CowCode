package com.gmail.mooman219.module.item.listener;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.item.Aspect;
import com.gmail.mooman219.module.item.InventoryDefaults;

public class ListenerPlayer implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        InventoryDefaults.setupPlayerInventory(event.getPlayer().getInventory());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.item().setPlayerInventory(player.getPlayer().getInventory().getContents());
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Aspect item = Aspect.get(event.getItemDrop().getItemStack());
        if(item.isUnmoveable()) {
            event.setCancelled(true);
        } else if(item.isSoulbound()) {
            event.getItemDrop().remove();
            WorldHelper.playSound(event.getPlayer().getLocation(), Sound.FIZZ);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        InventoryDefaults.setupPlayerInventory(event.getPlayer().getInventory());
    }
}
