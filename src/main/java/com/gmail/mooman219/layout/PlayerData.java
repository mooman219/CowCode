package com.gmail.mooman219.layout;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public abstract class PlayerData implements MongoData {
    public PlayerData(CDPlayer player, String tag) {
        this.player = player;
        this.tag = tag;
    }

    /**
     * Offline
     */
    private final String tag;

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public void load(DownloadReason reason, DBObject rank) {}

    @Override
    public DBObject save(UploadReason reason) {
        switch(reason) {
        case SAVE:
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    private final CDPlayer player;

    public CDPlayer getPlayer() {
        return player;
    }

    public void create() {}

    public void destroy() {}
}
