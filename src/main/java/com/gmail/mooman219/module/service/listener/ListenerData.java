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
import com.gmail.mooman219.frame.database.mongo.DownloadType;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.frame.event.CEventFactory;
import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.frame.event.DataRemovalEvent;
import com.gmail.mooman219.frame.event.DataVerifyEvent;
import com.gmail.mooman219.frame.scoreboard.Scoreboard;
import com.gmail.mooman219.frame.scoreboard.ScoreboardDisplayType;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.databse.task.DownloadTask;
import com.gmail.mooman219.handler.databse.task.UploadTask;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.module.DLPlayer;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.CMService;
import com.gmail.mooman219.module.service.store.PLService;

public class ListenerData implements Listener {
    @EventHandler
    public void onDataVerify(DataVerifyEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataVerify: " + event.getPlayerData().username);
    }

    @EventHandler
    public void onDataRemoval(DataRemovalEvent event) {
        Loader.info(CCService.cast + "[EVENT] Removal: (" + event.getPlayer().getName());
    }
    
    @EventHandler
    public void onDataCreate(DataCreateEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataCreate: " + event.getPlayer().getName());
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] PreLogin: " + event.getName());
        
        event.setKickMessage("");
        DownloadTask task = CHTask.manager.runSyncPluginTask(DownloadTask.get(event.getName(), DownloadType.LOGIN));
        if(task.playerData == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CMService.M_LOGINERROR);
            return;
        }
        CEventFactory.callDataVerifyEvent(event, task.playerData);
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            DLPlayer.set(event, task.playerData);
        } else if(event.getKickMessage().length() <= 0) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CMService.M_LOGINERROR);
        } else {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] Login: " + event.getPlayer().getName());

        DLPlayer playerData = DLPlayer.get(event.getPlayer());
        // Service - DataCreateEvent
        playerData.service = new PLService();
        playerData.service.scoreboard = new Scoreboard(playerData.username, ScoreboardDisplayType.SIDEBAR, playerData.serviceData.rank.color + playerData.username);
        //
        CEventFactory.callDataCreateEvent(event, event.getPlayer());
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        CHTask.manager.runAsyncPluginTask(UploadTask.get(UploadType.STATUS, playerData));
        TextHelper.message(event.getPlayer(), CMService.M_DATALOAD);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Loader.info(CCService.cast + "[EVENT] Join: " + event.getPlayer().getName());
        
        DLPlayer playerData = DLPlayer.get(event.getPlayer());
        playerData.service.scoreboard.addWatcher(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Loader.info(CCService.cast + "[EVENT] Quit: " + event.getPlayer().getName());
        
        CEventFactory.callDataRemovalEvent(true, event.getPlayer());
        CHTask.manager.runAsyncPluginTask(UploadTask.get(UploadType.NORMAL, DLPlayer.get(event.getPlayer())));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(PluginDisableEvent event) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            CEventFactory.callDataRemovalEvent(false, player);
        }
    }
}