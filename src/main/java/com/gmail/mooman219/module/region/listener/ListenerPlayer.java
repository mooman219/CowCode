package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.CDChunk;
import com.gmail.mooman219.module.CDPlayer;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            CDPlayer playerData = CDPlayer.get(event.getPlayer());
            CDChunk chunkData = CDChunk.get(event.getPlayer());
            playerData.service.scoreboard.modifyKeyName("regionn", Chat.GREEN + chunkData.getParentInformation().name);
            playerData.service.scoreboard.modifyKeyName("regionc", "• " + Chat.GREEN + chunkData.getParentInformation().combatType.name());
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        CDChunk chunkData = CDChunk.get(event.getPlayer());
        playerData.service.scoreboard.addKey("regionn", Chat.GREEN + chunkData.getParentInformation().name, 6);
        playerData.service.scoreboard.addKey("regionc", "• " + Chat.GREEN + chunkData.getParentInformation().combatType.name(), 5);
    }
}
