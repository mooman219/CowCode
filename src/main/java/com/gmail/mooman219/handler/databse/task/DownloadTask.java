package com.gmail.mooman219.handler.databse.task;

import com.gmail.mooman219.frame.database.mongo.DownloadType;
import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.CDPlayer;

public class DownloadTask extends CCTask {
    public final String username;
    public final DownloadType downloadType;
    public CDPlayer playerData;

    public DownloadTask(String username, DownloadType downloadType) {
        this.username = username;
        this.downloadType = downloadType;
        this.playerData = null;
    }

    public static DownloadTask get(String username, DownloadType downloadType) {
        return new DownloadTask(username, downloadType);
    }

    @Override
    public void run() {
        playerData = CHDatabase.manager.downloadPlayerData(username, downloadType);
    }
}