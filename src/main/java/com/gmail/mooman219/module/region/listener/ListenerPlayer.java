package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.text.Chat;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            CDPlayer playerData = CDPlayer.get(event.getPlayer());
            CDChunk chunkData = CDChunk.get(event.getTo());
            if(chunkData.getParentInfo().isLocked()) {
                event.setCancelled(true);
                return;
            }
            playerData.getSidebar().modifyName("regionn", Chat.GREEN + chunkData.getParentInfo().getName());
            String type;
            switch(chunkData.getParentInfo().getCombatType()) {
            case SAFE:
                type = Chat.GREEN + "" + Chat.BOLD + "Lawful";
                break;
            case CONTESTED:
                type = Chat.YELLOW + "" + Chat.BOLD + "Contested";
                break;
            // Chaotic is default because if we can't get the region, make the player think it's dangerous
            // just in case it reall is.
            case CHAOTIC:
            default:
                type = Chat.RED + "" + Chat.BOLD + "Chaotic";
                break;
            }
            playerData.getSidebar().modifyName("regionc", type);
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        CDChunk chunkData = CDChunk.get(event.getPlayer());
        playerData.getSidebar().addKey("regionn", Chat.GREEN + chunkData.getParentInfo().getName(), 6);
        playerData.getSidebar().addKey("regionc", "• " + Chat.GREEN + chunkData.getParentInfo().getCombatType().name(), 5);
    }
}
