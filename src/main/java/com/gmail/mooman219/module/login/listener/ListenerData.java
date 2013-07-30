package com.gmail.mooman219.module.login.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.service.event.DataCreateEvent;
import com.gmail.mooman219.module.service.event.DataRemovalEvent;
import com.gmail.mooman219.module.service.event.DataVerifyEvent;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onVerify(DataVerifyEvent event) {
        CDPlayer player = event.getPlayer();
        if(player.service().rank.index < Rank.MODERATOR.index) {
            long currentTime = TimeHelper.time();
            if(currentTime - player.login().lastlogin < ConfigGlobal.module.login.loginDelay) {
                event.getEvent().disallow(Result.KICK_OTHER, CCLogin.FRM.LOGINDELAY.parse(TimeHelper.getLargestType(ConfigGlobal.module.login.loginDelay - (currentTime - player.login().lastlogin), TimeType.MILLISECOND)));
                return;
            }
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