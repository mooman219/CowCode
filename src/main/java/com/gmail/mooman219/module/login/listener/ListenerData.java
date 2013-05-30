package com.gmail.mooman219.module.login.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.frame.event.DataRemovalEvent;
import com.gmail.mooman219.frame.event.DataVerifyEvent;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.time.TimeHelper;
import com.gmail.mooman219.frame.time.TimeType;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.login.CCLogin;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onVerify(DataVerifyEvent event) {
        if(event.getPlayer().serviceData.rank.index < Rank.MODERATOR.index) {
            long currentTime = System.currentTimeMillis();
            if(currentTime - event.getPlayer().loginData.lastlogin < ConfigGlobal.loginDelay) {
                try { // Sleep to prevent abusing players.
                    Thread.sleep(200);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                event.getEvent().disallow(Result.KICK_OTHER, CCLogin.FRM.LOGINDELAY.parse(TimeHelper.getLargestType(ConfigGlobal.loginDelay - (currentTime - event.getPlayer().loginData.lastlogin), TimeType.MILLISECOND)));
                return;
            }
        }
    }

    @EventHandler()
    public void onCreation(DataCreateEvent event) {
        long currentTime = System.currentTimeMillis();
        CDPlayer player = event.getPlayer();
        if(player.loginData.firstlogin == 0) {
            player.loginData.firstlogin = currentTime;
        }
        player.loginData.lastlogin = currentTime;
        player.loginData.isOnline = true;
    }

    @EventHandler()
    public void onRemoval(DataRemovalEvent event) {
        CDPlayer player = event.getPlayer();
        long currentTime = System.currentTimeMillis();
        player.loginData.lastKnownIP = player.getPlayer().getAddress().getAddress().getHostAddress();
        player.loginData.timeplayed += currentTime - player.loginData.lastlogin;
        player.loginData.lastlogin = currentTime;
        player.loginData.isOnline = false;
    }
}