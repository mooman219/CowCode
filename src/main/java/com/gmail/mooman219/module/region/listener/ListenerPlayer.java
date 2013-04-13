package com.gmail.mooman219.module.region.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import com.gmail.mooman219.frame.text.Chat;
import com.gmail.mooman219.module.region.PLRegion;
import com.gmail.mooman219.module.region.store.CFRegion;
import com.gmail.mooman219.module.service.PLService;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onMove(PlayerMoveEvent event) {
        if(event.getFrom().getChunk().getX() != event.getTo().getChunk().getX() || event.getFrom().getChunk().getZ() != event.getTo().getChunk().getZ()) {
            PLRegion region = event.getPlayer().getLive().get(PLRegion.class);
            PLService service = event.getPlayer().getLive().get(PLService.class);
            region.chunkRegion = CFRegion.getRegion(event.getTo().getChunk());
            service.scoreboard.modifyKeyName("regionn", Chat.GREEN + region.chunkRegion.getCSRegionInformation().name);
            service.scoreboard.modifyKeyName("regionc", "• " + Chat.GREEN + region.chunkRegion.getCSRegionInformation().combatType.name());
        }
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        PLRegion region = event.getPlayer().getLive().get(PLRegion.class);
        PLService service = event.getPlayer().getLive().get(PLService.class);
        region.chunkRegion = CFRegion.getRegion(event.getPlayer());
        service.scoreboard.addKey("regionn", Chat.GREEN + region.chunkRegion.getCSRegionInformation().name, 6);
        service.scoreboard.addKey("regionc", "• " + Chat.GREEN + region.chunkRegion.getCSRegionInformation().combatType.name(), 5);
    }
}
