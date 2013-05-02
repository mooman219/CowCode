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
import com.gmail.mooman219.frame.scoreboard.Scoreboard;
import com.gmail.mooman219.frame.scoreboard.ScoreboardDisplayType;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.database.DownloadReason;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.handler.database.UploadThread;
import com.gmail.mooman219.module.CDPlayer;
import com.gmail.mooman219.module.login.CMLogin;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.CMService;

public class ListenerData implements Listener {
    @EventHandler
    public void onDataVerify(DataVerifyEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataVerify: " + event.getPlayerData().username);
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
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CMService.M_LOGINERROR);
            return;
        }
        CEventFactory.callDataVerifyEvent(event, playerData);
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            CDPlayer.set(event, playerData);
        } else if(event.getKickMessage().length() <= 0) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CMService.M_LOGINERROR);
        } else {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] Login: " + event.getPlayer().getName());

        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        // Service - DataCreateEvent
        playerData.service.scoreboard = new Scoreboard(playerData.username, ScoreboardDisplayType.SIDEBAR, playerData.serviceData.rank.color + playerData.username);
        //
        CEventFactory.callDataCreateEvent(event, event.getPlayer());
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        CHDatabase.manager.uploadPlayer(playerData, UploadReason.STATUS, UploadThread.ASYNC);
        TextHelper.message(event.getPlayer(), CMService.M_DATALOAD);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Loader.info(CCService.cast + "[EVENT] Join: " + event.getPlayer().getName());

        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        playerData.service.scoreboard.addWatcher(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Loader.info(CCService.cast + "[EVENT] Quit: " + event.getPlayer().getName());

        CDPlayer playerData = CDPlayer.get(event.getPlayer());
        CHDatabase.manager.uploadPlayer(playerData, UploadReason.SAVE, UploadThread.ASYNC_REMOVE);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(PluginDisableEvent event) {
        Loader.info(CCService.cast + "Removing players");
        for(Player player : Bukkit.getOnlinePlayers()) {
            CDPlayer playerData = CDPlayer.get(player);
            player.kickPlayer(CMLogin.M_SHUTDOWN);
            Loader.info(CCService.cast + "[STOP] (" + Bukkit.getOnlinePlayers().length + ") normal: " + playerData.username);
        }
    }
}