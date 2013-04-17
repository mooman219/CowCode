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
import com.gmail.mooman219.module.DLPlayer;
import com.gmail.mooman219.module.login.CMLogin;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onVerify(DataVerifyEvent event) {
        if(event.getPlayerData().serviceData.rank.index < Rank.MODERATOR.index) {
            long currentTime = System.currentTimeMillis();
            if(currentTime - event.getPlayerData().loginData.lastlogin < ConfigGlobal.loginDelay) {
                event.getEvent().disallow(Result.KICK_OTHER, MessageFormat.format(CMLogin.F_LOGINDELAY, TimeHelper.getLargestType(ConfigGlobal.loginDelay - (currentTime - event.getPlayerData().loginData.lastlogin), TimeType.MILLISECOND)));
                return;
            }
        }
    }

    @EventHandler()
    public void onCreation(DataCreateEvent event) {
    	DLPlayer playerData = DLPlayer.get(event.getPlayer());
        long currentTime = System.currentTimeMillis();
        if(playerData.loginData.firstlogin == 0) {
        	playerData.loginData.firstlogin = currentTime;
        }
        playerData.loginData.lastlogin = currentTime;
        playerData.loginData.isOnline = true;
    }

    @EventHandler()
    public void onRemoval(DataRemovalEvent event) {
    	DLPlayer playerData = DLPlayer.get(event.getPlayer());
        long currentTime = System.currentTimeMillis();
        playerData.loginData.lastKnownIP = playerData.player.getAddress().getAddress().getHostAddress();
        playerData.loginData.timeplayed += currentTime - playerData.loginData.lastlogin;
        playerData.loginData.lastlogin = currentTime;
        playerData.loginData.isOnline = false;
    }
}