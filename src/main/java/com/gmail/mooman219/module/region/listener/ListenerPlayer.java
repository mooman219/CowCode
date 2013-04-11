package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.frame.MetaHelper;
import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.region.store.CFRegion;
import com.gmail.mooman219.module.service.player.PlayerData;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            PlayerData playerData = MetaHelper.getPlayerData(event.getPlayer());
            playerData.region.chunkRegion = CFRegion.getRegion(event.getTo().getChunk());
            playerData.service.scoreboard.modifyKeyName("regionn", Chat.GREEN + playerData.region.chunkRegion.getCSRegionInformation().name);
            playerData.service.scoreboard.modifyKeyName("regionc", "• " + Chat.GREEN + playerData.region.chunkRegion.getCSRegionInformation().combatType.name());
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        PlayerData playerData = MetaHelper.getPlayerData(event.getPlayer());
        playerData.region.chunkRegion = CFRegion.getRegion(event.getPlayer());
        playerData.service.scoreboard.addKey("regionn", Chat.GREEN + playerData.region.chunkRegion.getCSRegionInformation().name, 6);
        playerData.service.scoreboard.addKey("regionc", "• " + Chat.GREEN + playerData.region.chunkRegion.getCSRegionInformation().combatType.name(), 5);
    }
}
