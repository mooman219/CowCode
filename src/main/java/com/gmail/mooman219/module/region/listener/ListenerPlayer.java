package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.CEventFactory;
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
            if(!RegionManager.compare(playerData.region.currentRegion, region)) {
                if(region.isLocked()) {
                    CCRegion.MSG.LOCKED.send(playerData);
                    event.setCancelled(true);
                    return;
                } else if(CEventFactory.callRegionChangeEvent(event, playerData, playerData.region.currentRegion, region).isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                playerData.region.currentRegion = region;
                //
                playerData.getSidebar().modifyName("regionn", Chat.GREEN + region.getName());
                String type;
                switch(region.getCombatType()) {
                case SAFE:
                    type = Chat.GREEN + "" + Chat.BOLD + "Lawful";
                    break;
                case CONTESTED:
                    type = Chat.YELLOW + "" + Chat.BOLD + "Contested";
                    break;
                case CHAOTIC: // Chaotic is default because if we can't get the region, make the player think it's dangerous just in case it really is.
                default:
                    type = Chat.RED + "" + Chat.BOLD + "Chaotic";
                    break;
                }
                playerData.getSidebar().modifyName("regionc", type);
            }
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        BasicRegion region = RegionManager.getRegion(event.getPlayer().getLocation());
        playerData.region.currentRegion = region;
        playerData.getSidebar().addKey("regionn", Chat.GREEN + region.getName(), 6);
        playerData.getSidebar().addKey("regionc", "• " + Chat.GREEN + region.getCombatType().name(), 5);
    }
}
