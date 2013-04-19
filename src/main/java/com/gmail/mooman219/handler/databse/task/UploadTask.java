package com.gmail.mooman219.handler.databse.task;

import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.CDPlayer;

public class UploadTask extends CCTask {
    public final UploadType uploadType;
    public final CDPlayer playerData;

    public UploadTask(UploadType uploadType, CDPlayer playerData) {
        this.uploadType = uploadType;
        this.playerData = playerData;
    }

    public static UploadTask get(UploadType uploadType, CDPlayer playerData) {
        return new UploadTask(uploadType, playerData);
    }

    @Override
    public void run() {
        CHDatabase.manager.uploadPlayerData(playerData, uploadType);
    }
}