package com.gmail.mooman219.handler.database;

import java.util.concurrent.Callable;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.mongodb.BasicDBObject;
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
                playerObject = findPlayerObject(username, true);
                Loader.info(CHDatabase.getCast() + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                if(playerObject == null) {
                    createPlayerObject(username);
                    CHDatabase.getManager().uploadPlayer(player, UploadReason.SAVE, false, false);
                }
                if(player.load(reason, playerObject)) {
                    Loader.warning(CHDatabase.getCast() + "[DOWN] Unable to load playerdata for '" + player.getUsername() + "'");
                    return null;
                }
                return player;
            case QUERY:
                playerObject = findPlayerObject(username, false);
                Loader.info(CHDatabase.getCast() + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                if(playerObject != null) {
                    player = new CDPlayer(username);
                    player.load(reason, playerObject);
                    return player;
                } else {
                    return null;
                }
            default:
                return null;
            }
        } catch(Exception e) {
            Loader.warning("Something has gone wrong during " + username + "'s download request.");
            Loader.warning(CHDatabase.getCast() + "Currently" + (CHDatabase.getManager().isConnected() ? " " : " not ") + "connected to database.");
            e.printStackTrace();
        }
        return null;
    }

    public DBObject findPlayerObject(String username, boolean caseSensitive) {
        BasicDBObject query = caseSensitive ? new BasicDBObject("username", username) : new BasicDBObject("usernamelowercase", username.toLowerCase());
        return CHDatabase.getManager().getUsers().findOne(query);
    }

    public DBObject createPlayerObject(String username) {
        BasicDBObject playerObject = new BasicDBObject()
        .append("username", username)
        .append("usernamelowercase", username.toLowerCase());
        CHDatabase.getManager().getUsers().insert(playerObject);
        return playerObject;
    }
}
