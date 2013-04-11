package com.gmail.mooman219.handler.task.task;

import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.service.player.PlayerData;

public class DownloadTask extends CCTask {
    public final String username;
    public final boolean create;
    public final boolean caseSensitive;
    public PlayerData playerData;

    public DownloadTask(String username, boolean create, boolean caseSensitive) {
        this.username = username;
        this.create = create;
        this.caseSensitive = caseSensitive;
        this.playerData = null;
    }
    
    public static DownloadTask get(String username, boolean create, boolean caseSensitive) {
        return new DownloadTask(username, create, caseSensitive);
    }

    public void run() {
        playerData = CHDatabase.manager.downloadPlayerData(username, create, caseSensitive);
    }
}