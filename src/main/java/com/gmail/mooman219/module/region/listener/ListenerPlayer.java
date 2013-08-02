package com.gmail.mooman219.module.region.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.EventHelper;
import com.gmail.mooman219.frame.LocationHelper;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.event.RegionChangeEvent;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class ListenerPlayer implements Listener{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(PlayerMoveEvent event) {
        // Checks to see if the player has moved into a new chunk
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            CDPlayer player = CDPlayer.get(event.getPlayer());
            BasicRegion toRegion = RegionManager.getRegion(event.getTo());
            BasicRegion fromRegion = player.region().getRegion();
            // If the to and from regions are the same, just let them move w/o anymore checks.
            if(!toRegion.equals(fromRegion)) {
                if(toRegion.isLocked()) {
                    if(fromRegion.isLocked()) {
                        // They broke it, let them in :\
                        // If we don't let them in, they'll be glitched inbetween the 2 locked regions.
                        CCRegion.MSG.LOCKEDFAIL.send(player);
                    } else {
                        CCRegion.MSG.LOCKED.send(player);
                        cancelPlayerMoveEvent(event);
                        // Remove because abuse
                        //VectorHelper.pushAwayFromPoint(event.getPlayer(), event.getTo(), 1.0, new Vector(0, 0.4, 0));
                        player.sendBlockChange(event.getTo(), Material.GLASS);
                        player.sendBlockChange(event.getTo().clone().add(0, 1, 0), Material.GLASS);
                        player.sendBlockChange(event.getTo().clone().add(0, 2, 0), Material.GLASS);
                        return;
                    }
                } else if(EventHelper.callEvent(new RegionChangeEvent(event, player, player.region().getRegion(), toRegion)).isCancelled()) {
                    cancelPlayerMoveEvent(event);
                    return;
                }
                player.region().setRegion(toRegion);
            }
        }
    }

    public void cancelPlayerMoveEvent(PlayerMoveEvent event) {
        event.setCancelled(true);
        event.setFrom(LocationHelper.getBlockCenter(event.getFrom()));
        if(event.getPlayer().getVehicle() != null) {
            event.getPlayer().getVehicle().eject();
            // The extra teleport is needed because fuck minecraft
            event.getPlayer().teleport(LocationHelper.getBlockCenter(event.getFrom()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        BasicRegion region = RegionManager.getRegion(event.getPlayer().getLocation());
        player.getSidebar().addKey("regionn", Chat.GREEN + region.getName(), 6);
        player.getSidebar().addKey("regionc", region.getCombatType().getColoredName(), 5);
        player.region().setRegion(region);
    }

    @EventHandler()
    public void onRespawn(PlayerRespawnEvent event){
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.region().setRegion(RegionManager.getRegion(event.getRespawnLocation()));
    }
}
