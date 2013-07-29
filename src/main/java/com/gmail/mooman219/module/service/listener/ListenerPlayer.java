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

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.handler.database.CHDatabase;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.module.login.CCLogin;
import com.gmail.mooman219.module.service.CCService;

public class ListenerPlayer implements Listener {
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPreLogin(AsyncPlayerPreLoginEvent event) {
        // Set the kick message blank so that I can tell if another module changed it
        event.setKickMessage("");
        CDPlayer player = CHDatabase.getManager().downloadPlayer(event.getName(), DownloadReason.LOGIN);
        // If there is no playerdata, don't let the player join, else MAJOR problems.
        if(player == null) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CCService.MSG.LOGINERROR + "");
            return;
        }
        CEventFactory.callDataVerifyEvent(event, player);
        if(event.getLoginResult() == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            player.processPreLogin();
            CDPlayer.set(event, player);
        } else if(event.getKickMessage().length() <= 0) {
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, CCService.MSG.LOGINERROR + "");
        } else {
            event.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onLogin(PlayerLoginEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.processLogin(event.getPlayer());
        CEventFactory.callDataCreateEvent(event, player);
        event.setResult(PlayerLoginEvent.Result.ALLOWED);
        CHDatabase.getManager().uploadPlayer(player, UploadReason.STATUS, false, true);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent event) {
        CDPlayer player = CDPlayer.get(event.getPlayer());
        player.processJoin();
        CCService.MSG.DATALOAD.send(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onQuit(PlayerQuitEvent event) {
        CDPlayer player = CDPlayer.getSafe(event.getPlayer());
        if(player == null) {
            Loader.warning(CCService.getCast() + "onQuit(): Null player '" + event.getPlayer().getName() + "'");
            return;
        }
        player.processQuit();
        CHDatabase.getManager().uploadPlayer(player, UploadReason.SAVE, true, true);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDisable(PluginDisableEvent event) {
        Loader.info(CCService.getCast() + "Removing players");
        for(Player player : Bukkit.getOnlinePlayers()) {
            CCLogin.MSG.SHUTDOWN.kick(player);
        }
    }
}