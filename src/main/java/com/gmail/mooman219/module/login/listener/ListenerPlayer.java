package com.gmail.mooman219.module.login.listener;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.mooman219.bull.CDPlayer;

public class ListenerPlayer implements Listener {
    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        // IP storage (Because it's not available in Login)
        String ip = player.getPlayer().getAddress().getAddress().getHostAddress();
        player.login().lastKnownIP = ip;
        if(!player.login().knownIPs.contains(ip)) {
            player.login().knownIPs.add(ip);
        }
        // Gamemode correction
        if(player.getPlayer().getGameMode() == GameMode.SURVIVAL) {
            player.getPlayer().setGameMode(GameMode.ADVENTURE);
        }
        // Position saving
        Location position = player.login().getPosition();
        if(position != null) {
            player.getPlayer().teleport(position);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.login().setPosition(player.getPlayer().getLocation());
    }
}