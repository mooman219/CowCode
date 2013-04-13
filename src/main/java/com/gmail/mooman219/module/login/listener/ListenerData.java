package com.gmail.mooman219.module.login.listener;

import java.text.MessageFormat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.frame.event.DataRemovalEvent;
import com.gmail.mooman219.frame.event.DataVerifyEvent;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.login.CMLogin;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onVerify(DataVerifyEvent event) {
        if(event.playerData.serviceData.rank.index < Rank.MODERATOR.index) {
            long currentTime = System.currentTimeMillis();
            if(currentTime - event.playerData.loginData.lastlogin < ConfigGlobal.loginDelay) {
                event.event.disallow(Result.KICK_OTHER, MessageFormat.format(CMLogin.F_LOGINDELAY, TimeHelper.getLargestType(ConfigGlobal.loginDelay - (currentTime - event.playerData.loginData.lastlogin), TimeType.MILLISECOND)));
                return;
            }
        }
    }

    @EventHandler()
    public void onCreation(DataCreateEvent event) {
        long currentTime = System.currentTimeMillis();
        if(event.playerData.loginData.firstlogin == 0) {
            event.playerData.loginData.firstlogin = currentTime;
        }
        event.playerData.loginData.lastlogin = currentTime;
        event.playerData.loginData.isOnline = true;
    }

    @EventHandler()
    public void onRemoval(DataRemovalEvent event) {
        long currentTime = System.currentTimeMillis();
        event.playerData.loginData.lastKnownIP = event.playerData.player.getAddress().getAddress().getHostAddress();
        event.playerData.loginData.timeplayed += currentTime - event.playerData.loginData.lastlogin;
        event.playerData.loginData.lastlogin = currentTime;
        event.playerData.loginData.isOnline = false;
    }
}