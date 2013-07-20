package com.gmail.mooman219.module.item.listener;

import java.util.Iterator;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import com.gmail.mooman219.module.item.inventory.ItemDefaults;

public class ListenerPlayer implements Listener {
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if(isLocked(event.getItem())) {
            event.setCancelled(true);
            event.getPlayer().updateInventory();
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ItemDefaults.playerInv.apply(event.getPlayer().getInventory());
    }

    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        if(isLocked(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        ItemDefaults.playerInv.apply(event.getPlayer().getInventory());
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Iterator<ItemStack> iterator = event.getDrops().iterator();
        while(iterator.hasNext()) {
            ItemStack itemStack = iterator.next();
            if(isLocked(itemStack)) {
                iterator.remove();
            }
        }
    }

    public static boolean isLocked(ItemStack item) {
        if(item == null) {
            return false;
        }
        switch(item.getTypeId()) {
        case 371:
            return true;
        }
        return false;
    }
}
