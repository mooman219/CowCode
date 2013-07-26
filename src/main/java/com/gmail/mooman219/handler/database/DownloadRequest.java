package com.gmail.mooman219.handler.database;

import java.util.concurrent.Callable;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.mongodb.DBObject;

class PlayerDownloader implements Callable<CDPlayer> {
    public final String username;
    public final DownloadReason reason;

    public PlayerDownloader(String username, DownloadReason reason) {
        this.username = username;
        this.reason = reason;
    }

    @Override
    public CDPlayer call() {
        try {
            CDPlayer player;
            DBObject playerObject;
            switch(reason) {
            case LOGIN:
                player = new CDPlayer(username);
                playerObject = CHDatabase.manager.downloadPlayerObject(username, true);
                Loader.info(CHDatabase.cast + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                if(playerObject == null) {
                    CHDatabase.manager.createPlayerObject(username);
                    CHDatabase.manager.uploadPlayer(player, UploadReason.SAVE, false, false);
                } else {
                    if(player.sync(reason, playerObject)) {
                        return null;
                    }
                }
                return player;
            case QUERY:
                playerObject = CHDatabase.manager.downloadPlayerObject(username, false);
                Loader.info(CHDatabase.cast + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                if(playerObject != null) {
                    player = new CDPlayer(username);
                    player.sync(reason, playerObject);
                    return player;
                } else {
                    return null;
                }
            default:
                return null;
            }
        } catch(Exception e) {
            Loader.warning("Something has gone wrong during " + username + "'s download request.");
            Loader.warning(CHDatabase.cast + "Currently" + (CHDatabase.manager.isConnected() ? " " : " not ") + "connected to database.");
            e.printStackTrace();
        }
        return null;
    }
}
