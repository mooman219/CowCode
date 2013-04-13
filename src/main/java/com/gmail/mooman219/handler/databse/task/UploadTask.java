package com.gmail.mooman219.handler.databse.task;

import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.service.PlayerData;

public class UploadTask extends CCTask {
    public final UploadType uploadType;
    public final PlayerData playerData;

    public UploadTask(UploadType uploadType, PlayerData playerData) {
        this.uploadType = uploadType;
        this.playerData = playerData;
    }
    
    public static UploadTask get(UploadType uploadType, PlayerData playerData) {
        return new UploadTask(uploadType, playerData);
    }

    public void run() {
        CHDatabase.manager.uploadPlayerData(playerData, uploadType);
    }
}