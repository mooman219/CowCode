package com.gmail.mooman219.module.chat.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.module.chat.CMChat;

public class ListenerPlayer implements Listener{
    @EventHandler()
    public void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(TextHelper.parse(CMChat.F_LOGIN, event.getPlayer().getUsername()));
    }

    @EventHandler()
    public void onKick(PlayerKickEvent event) {
        event.setLeaveMessage(TextHelper.parse(CMChat.F_KICK, event.getPlayer().getUsername()));
    }

    @EventHandler()
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(TextHelper.parse(CMChat.F_QUIT, event.getPlayer().getUsername()));
    }
}
