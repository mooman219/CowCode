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
            CDChunk chunkData = CDChunk.get(event.getPlayer());
            playerData.getSidebar().modifyName("regionn", Chat.GREEN + chunkData.getParentInformation().getName());
            playerData.getSidebar().modifyName("regionc", "• " + Chat.GREEN + chunkData.getParentInformation().getCombatType().name());
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        CDChunk chunkData = CDChunk.get(event.getPlayer());
        playerData.getSidebar().addKey("regionn", Chat.GREEN + chunkData.getParentInformation().getName(), 6);
        playerData.getSidebar().addKey("regionc", "• " + Chat.GREEN + chunkData.getParentInformation().getCombatType().name(), 5);
    }
}
