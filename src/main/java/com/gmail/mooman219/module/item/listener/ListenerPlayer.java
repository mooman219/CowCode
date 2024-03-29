package com.gmail.mooman219.module.item.listener;

import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.module.item.api.InventoryHelper;
import com.gmail.mooman219.module.item.api.aspect.Aspect;

public class ListenerPlayer implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.getPlayer().getInventory().setContents(player.item().getPlayerInventory());
        InventoryHelper.applyPlayerInventory(event.getPlayer().getInventory());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.item().setPlayerInventory(player.getPlayer().getInventory().getContents());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDropItem(PlayerDropItemEvent event) {
        Aspect item = Aspect.get(event.getItemDrop().getItemStack());
        if(item.isButton() || item.isUnmoveable()) {
            event.setCancelled(true);
        } else if(item.isSoulbound()) {
            event.getItemDrop().remove();
            WorldHelper.playSound(event.getPlayer().getLocation(), Sound.FIZZ);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        InventoryHelper.applyPlayerInventory(event.getPlayer().getInventory());
    }
}
