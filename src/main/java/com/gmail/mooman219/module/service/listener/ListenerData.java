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

import com.gmail.mooman219.bullbukkit.CDPlayer;
import com.gmail.mooman219.bullbukkit.PlayerShutdownType;
import com.gmail.mooman219.bullbukkit.PlayerStartupType;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.event.CEventFactory;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.database.DownloadReason;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.service.CCService;

public class ListenerData implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        Loader.info(CCService.cast + "[EVENT] PreLogin: " + event.getName());

        // Kick the player if they do not have a minecraft account :)
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.KICK_VERIFY) {
            event.setKickMessage(CCService.MSG.USERNAMEFAIL + "");
            return;
        }
        // Set the kick message blank so that I can tell if another module changed it
        event.setKickMessage("");
        CDPlayer player = CHDatabase.manager.downloadPlayer(event.getName(), DownloadReason.LOGIN);
        // If there is no playerdata, don't let the player join, else MAJOR problems.
        if(player == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CCService.MSG.LOGINERROR + "");
            return;
        }
        CEventFactory.callDataVerifyEvent(event, player);
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            player.startup(null, PlayerStartupType.POST_VERIFY);
            CDPlayer.set(event, player);
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
        player.startup(event.getPlayer(), PlayerStartupType.PRE_CREATION);
        CEventFactory.callDataCreateEvent(event, player);
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        CHDatabase.manager.uploadPlayer(player, UploadReason.STATUS, false, true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        Loader.info(CCService.cast + "[EVENT] Join: " + event.getPlayer().getName());

        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.startup(event.getPlayer(), PlayerStartupType.PRE_JOIN);
        CCService.MSG.DATALOAD.send(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        Loader.info(CCService.cast + "[EVENT] Quit: " + event.getPlayer().getName());

        CDPlayer player = CDPlayer.getSafe(event.getPlayer());
        if(player == null) {
            return;
        }
        player.shutdown(PlayerShutdownType.POST_QUIT);
        CHDatabase.manager.uploadPlayer(player, UploadReason.SAVE, true, true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(PluginDisableEvent event) {
        Loader.info(CCService.cast + "Removing players");
        for(Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(CCLogin.MSG.SHUTDOWN + "");
        }
    }
}