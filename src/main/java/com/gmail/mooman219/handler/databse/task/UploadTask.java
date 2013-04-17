package com.gmail.mooman219.handler.databse.task;

import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.DLPlayer;

public class UploadTask extends CCTask {
    public final UploadType uploadType;
    public final DLPlayer playerData;

    public UploadTask(UploadType uploadType, DLPlayer playerData) {
        this.uploadType = uploadType;
        this.playerData = playerData;
    }
    
    public static UploadTask get(UploadType uploadType, DLPlayer playerData) {
        return new UploadTask(uploadType, playerData);
    }

    public void run() {
        CHDatabase.manager.uploadPlayerData(playerData, uploadType);
    }
}