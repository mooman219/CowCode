package com.gmail.mooman219.module.damage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.module.damage.CCDamage;

public class ListenerPlayer implements Listener {
    @EventHandler()
    public void onRespawn(PlayerRespawnEvent e){
        final CDPlayer player = CDPlayer.get(e.getPlayer());
        player.resetHealth();
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent e) {
        CDPlayer player = CDPlayer.get(e.getPlayer());
        CCDamage.healthBoard.addPlayer(player);
        player.getSidebar().addKey("hp", CCDamage.FRM.BARHEALTH.parse(player.stat.healthCur), 9);
    }
}
