package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.region.CCRegion;
import com.gmail.mooman219.module.region.RegionManager;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            CDChunk chunkData = CDChunk.get(event.getTo());
            CDPlayer playerData = CDPlayer.get(event.getPlayer());
            if(!RegionManager.compare(playerData.region.currentRegion, chunkData.getRegion())) {
                if(chunkData.getRegion().isLocked()) {
                    CCRegion.MSG.LOCKED.send(playerData);
                    event.setCancelled(true);
                    return;
                }
                if(CEventFactory.callRegionChangeEvent(event, playerData, playerData.region.currentRegion, chunkData.getRegion()).isCancelled()) {
                    event.setCancelled(true);
                    return;
                }
                //
                playerData.getSidebar().modifyName("regionn", Chat.GREEN + chunkData.getRegion().getName());
                String type;
                switch(chunkData.getRegion().getCombatType()) {
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
        CDChunk chunkData = CDChunk.get(event.getPlayer());
        playerData.getSidebar().addKey("regionn", Chat.GREEN + chunkData.getRegion().getName(), 6);
        playerData.getSidebar().addKey("regionc", "• " + Chat.GREEN + chunkData.getRegion().getCombatType().name(), 5);
    }
}
