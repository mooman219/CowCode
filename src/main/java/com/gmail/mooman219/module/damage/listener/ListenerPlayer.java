package com.gmail.mooman219.module.damage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.damage.CCDamage;

public class ListenerPlayer implements Listener {
    @EventHandler()
    public void onRespawn(PlayerRespawnEvent event){
        final CDPlayer player = CDPlayer.get(event.getPlayer());
        player.resetHealth();
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.updateHealth(false);
        CCDamage.healthBoard.addPlayer(player);
        player.getSidebar().addKey("hp", CCDamage.FRM.BARHEALTH.parse(player.stat.healthCur), 9);
    }
}
