package com.gmail.mooman219.module.service.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.event.CEventFactory;
import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.frame.event.DataRemovalEvent;
import com.gmail.mooman219.frame.event.DataVerifyEvent;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.database.DownloadReason;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.handler.database.UploadThread;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.service.CCService;

public class ListenerData implements Listener {
    @EventHandler
    public void onDataVerify(DataVerifyEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataVerify: " + event.getPlayer().username);
    }

    @EventHandler
    public void onDataRemoval(DataRemovalEvent event) {
        Loader.info(CCService.cast + "[EVENT] Removal: " + event.getPlayer().getName());
    }

    @EventHandler
    public void onDataCreate(DataCreateEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataCreate: " + event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] PreLogin: " + event.getName());

        event.setKickMessage("");
        CDPlayer playerData = CHDatabase.manager.downloadPlayer(event.getName(), DownloadReason.LOGIN);
        if(playerData == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CCService.MSG.LOGINERROR + "");
            return;
        }
        CEventFactory.callDataVerifyEvent(event, playerData);
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            CDPlayer.set(event, playerData);
        } else if(event.getKickMessage().length() <= 0) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CCService.MSG.LOGINERROR + "");
        } else {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] Login: " + event.getPlayer().getName());

        CDPlayer player = CDPlayer.get(event.getPlayer());
        CEventFactory.callDataCreateEvent(event, player);
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        CHDatabase.manager.uploadPlayer(player, UploadReason.STATUS, UploadThread.ASYNC);
        CCService.MSG.DATALOAD.send(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Loader.info(CCService.cast + "[EVENT] Join: " + event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Loader.info(CCService.cast + "[EVENT] Quit: " + event.getPlayer().getName());

        CDPlayer player = CDPlayer.get(event.getPlayer());
        CHDatabase.manager.uploadPlayer(player, UploadReason.SAVE, UploadThread.ASYNC_REMOVE);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(PluginDisableEvent event) {
        Loader.info(CCService.cast + "Removing players");
        for(Player player : Bukkit.getOnlinePlayers()) {
            CDPlayer playerData = CDPlayer.get(player);
            player.kickPlayer(CCLogin.MSG.SHUTDOWN + "");
            Loader.info(CCService.cast + "[STOP] (" + Bukkit.getOnlinePlayers().length + ") normal: " + playerData.username);
        }
    }
}