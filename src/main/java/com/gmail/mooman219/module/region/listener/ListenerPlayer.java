package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.frame.VectorHelper;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class ListenerPlayer implements Listener{
    @EventHandler(priority = EventPriority.HIGH)
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            BasicRegion region = RegionManager.getRegion(event.getTo());
            CDPlayer playerData = CDPlayer.get(event.getPlayer());
            if(!region.equals(playerData.region.currentRegion)) {
                // Check so player doesn't get in infini loop of fail
                if(region.isLocked()) {
                    CCRegion.MSG.LOCKED.send(playerData);
                    event.setCancelled(true);
                    VectorHelper.pushAwayFromPoint(event.getPlayer(), event.getTo(), 1.0, new Vector(0, 0.4, 0));
                    return;
                } else if(CEventFactory.callRegionChangeEvent(event, playerData, playerData.region.currentRegion, region).isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                playerData.region.currentRegion = region;
                //
                playerData.getSidebar().modifyName("regionn", Chat.GREEN + region.getName());
                playerData.getSidebar().modifyName("regionc", region.getCombatType().getColoredName());
            }
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        BasicRegion region = RegionManager.getRegion(event.getPlayer().getLocation());
        playerData.region.currentRegion = region;
        playerData.getSidebar().addKey("regionn", Chat.GREEN + region.getName(), 6);
        playerData.getSidebar().addKey("regionc", region.getCombatType().getColoredName(), 5);
    }
}
