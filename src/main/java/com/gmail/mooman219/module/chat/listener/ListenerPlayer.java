package com.gmail.mooman219.module.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.mooman219.module.chat.CCChat;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(CCChat.FRM.LOGIN.parse(event.getPlayer().getName()));
    }

    @EventHandler()
    public void onKick(PlayerKickEvent event) {
        event.setLeaveMessage(CCChat.FRM.KICK.parse(event.getPlayer().getName()));
    }

    @EventHandler()
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(CCChat.FRM.QUIT.parse(event.getPlayer().getName()));
    }

    @EventHandler()
    public void onDeath(PlayerDeathEvent event) {
        event.setDeathMessage(CCChat.FRM.DEATH.parse(event.getEntity().getName()));
    }
}
