package com.gmail.mooman219.handler.task.task;

import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.frame.event.CCEventFactory;
import com.gmail.mooman219.handler.databse.CHDatabase;
import com.gmail.mooman219.handler.task.type.CCTask;
import com.gmail.mooman219.module.service.player.PlayerData;

public class UploadTask extends CCTask {
    public final UploadType uploadType;
    public final PlayerData playerData;
    public final boolean triggerRemoval;

    public UploadTask(UploadType uploadType, PlayerData playerData, boolean triggerRemoval) {
        this.uploadType = uploadType;
        this.playerData = playerData;
        this.triggerRemoval = triggerRemoval;
    }
    
    public static UploadTask get(UploadType uploadType, PlayerData playerData, boolean triggerRemoval) {
        return new UploadTask(uploadType, playerData, triggerRemoval);
    }

    public void run() {
        if(triggerRemoval) {
            CCEventFactory.callDataRemovalEvent(true, playerData);
        }
        CHDatabase.manager.uploadPlayerData(playerData, uploadType);
    }
}