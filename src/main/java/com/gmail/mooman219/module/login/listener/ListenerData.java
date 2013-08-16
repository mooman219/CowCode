package com.gmail.mooman219.module.login.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.login.store.ConfigLogin;
import com.gmail.mooman219.module.service.api.DataCreateEvent;
import com.gmail.mooman219.module.service.api.DataRemovalEvent;
import com.gmail.mooman219.module.service.api.DataVerifyEvent;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onVerify(DataVerifyEvent event) {
        CDPlayer player = event.getPlayer();
        long currentTime = TimeHelper.time();
        if(currentTime - player.login().lastlogin < ConfigLogin.getData().loginDelay) {
            event.getEvent().disallow(Result.KICK_OTHER, CCLogin.FRM.LOGINDELAY.parse(TimeHelper.getLargestType(ConfigLogin.getData().loginDelay - (currentTime - player.login().lastlogin), TimeType.MILLISECOND)));
            return;
        }
    }

    @EventHandler()
    public void onCreation(DataCreateEvent event) {
        CDPlayer player = event.getPlayer();
        long currentTime = TimeHelper.time();
        if(player.login().firstlogin == 0) {
            player.login().firstlogin = currentTime;
        }
        player.login().lastlogin = currentTime;
        player.login().isOnline = true;
    }

    @EventHandler()
    public void onRemoval(DataRemovalEvent event) {
        CDPlayer player = event.getPlayer();
        long currentTime = TimeHelper.time();
        player.login().timeplayed += currentTime - player.login().lastlogin;
        player.login().lastlogin = currentTime;
        player.login().isOnline = false;
    }
}