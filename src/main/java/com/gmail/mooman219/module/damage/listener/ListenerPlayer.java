package com.gmail.mooman219.module.damage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;

public class ListenerPlayer implements Listener {
    @EventHandler()
    public void onRespawn(PlayerRespawnEvent e){
        CDPlayer player = CDPlayer.get(e.getPlayer());
        player.resetHealth();
    }
}
