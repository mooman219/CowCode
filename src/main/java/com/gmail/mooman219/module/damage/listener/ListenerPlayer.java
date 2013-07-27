package com.gmail.mooman219.module.damage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.damage.CCDamage;

public class ListenerPlayer implements Listener {
    @EventHandler()
    public void onRespawn(PlayerRespawnEvent event){
        final CDPlayer player = CDPlayer.get(event.getPlayer());
        player.resetHealth();
        CHTask.manager.runBukkit(new Runnable() {
            @Override
            public void run() {
                player.updateHealth();
            }
        }, 3);
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.getSidebar().addKey("hp", CCDamage.FRM.BARHEALTH.parse(player.stat.healthCur), 9);
        CCDamage.healthBoard.addPlayer(player);
        player.setHealth(player.stat.healthCur);
    }
}
