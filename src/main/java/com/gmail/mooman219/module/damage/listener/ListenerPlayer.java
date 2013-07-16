package com.gmail.mooman219.module.damage.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.PacketHelper;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.damage.CCDamage;

public class ListenerPlayer implements Listener {
    @EventHandler()
    public void onRespawn(PlayerRespawnEvent e){
        final CDPlayer player = CDPlayer.get(e.getPlayer());
        player.resetHealth();
        CHTask.manager.runBukkit(new Runnable() {
            @Override
            public void run() {
                if(player.isDead()) {                    
                    player.sendPacket(PacketHelper.getForceRespawn());
                }
            }
        }, 40);
    }

    @EventHandler()
    public void onJoin(PlayerJoinEvent e) {
        CDPlayer player = CDPlayer.get(e.getPlayer());
        player.getSidebar().addKey("hp", CCDamage.FRM.BARHEALTH.parse(player.stat.healthCur), 9);
    }
}
