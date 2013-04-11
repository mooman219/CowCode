package com.gmail.mooman219.module.service.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.MetaHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.frame.event.CCEventFactory;
import com.gmail.mooman219.frame.event.DataCreateEvent;
import com.gmail.mooman219.frame.event.DataRemovalEvent;
import com.gmail.mooman219.frame.event.DataVerifyEvent;
import com.gmail.mooman219.frame.scoreboard.Scoreboard;
import com.gmail.mooman219.frame.scoreboard.ScoreboardDisplayType;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.handler.task.task.DownloadTask;
import com.gmail.mooman219.handler.task.task.UploadTask;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.CMService;
import com.gmail.mooman219.module.service.PLService;
import com.gmail.mooman219.module.service.player.PlayerData;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDataVerify(DataVerifyEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataVerify: " + event.playerData.username);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDataRemoval(DataRemovalEvent event) {
        Loader.info(CCService.cast + "[EVENT] Removal: (" + Bukkit.getOfflinePlayers().length + ") " + event.playerData.username);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] PreLogin: " + event.getName());
        event.setKickMessage("");
        DownloadTask task = CHTask.manager.runSyncPluginTask(DownloadTask.get(event.getName(), true, true));
        if(task.playerData == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CMService.M_LOGINERROR);
            return;
        }
        CCEventFactory.callDataVerifyEvent(event, task.playerData);
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            MetaHelper.getStore(event).set("playerdata", task.playerData);
        } else if(event.getKickMessage().length() <= 0) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CMService.M_LOGINERROR);
        } else {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] Login: " + event.getPlayer().getName());
        PlayerData playerData = MetaHelper.getPlayerData(event.getPlayer());
        CCEventFactory.callDataCreateEvent(event, playerData);
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        CHTask.manager.runAsyncPluginTask(UploadTask.get(UploadType.STATUS, playerData, false));
        TextHelper.message(event.getPlayer(), CMService.M_DATALOAD);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDataCreate(DataCreateEvent event) {
        Loader.info(CCService.cast + "[EVENT] DataCreate: " + event.playerData.username);
        event.playerData.service = new PLService();
        event.playerData.service.scoreboard = new Scoreboard(event.playerData.username, ScoreboardDisplayType.SIDEBAR, event.playerData.serviceData.rank.color + event.playerData.username);
        //event.playerData.service.scoreboard.addWatcher(event.playerData.player);
    }
    
    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        PlayerData playerData = MetaHelper.getPlayerData(event.getPlayer());
        playerData.service.scoreboard.addWatcher(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Loader.info(CCService.cast + "[EVENT] Quit: " + event.getPlayer().getName());
        CHTask.manager.runAsyncPluginTask(UploadTask.get(UploadType.NORMAL, MetaHelper.getPlayerData(event.getPlayer()), true));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(PluginDisableEvent event) {
        for(PlayerData playerData : MetaHelper.getAllPlayerData()) {
            CCEventFactory.callDataRemovalEvent(false, playerData);
        }
    }
}