package com.gmail.mooman219.module.service.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.MetaHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.handler.task.task.UploadTask;
import com.gmail.mooman219.module.login.CMLogin;
import com.gmail.mooman219.module.service.CCService;

public class PlayerManager {

    public static void stop() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PlayerData playerData = MetaHelper.getPlayerData(player);
            CHTask.manager.runAsyncPluginTask(UploadTask.get(UploadType.NORMAL, playerData, false));
            player.kickPlayer(CMLogin.M_SHUTDOWN);
            Loader.info(CCService.cast + "[STOP] (" + Bukkit.getOnlinePlayers().length + ") normal: " + playerData.username);
        }
    }
}
