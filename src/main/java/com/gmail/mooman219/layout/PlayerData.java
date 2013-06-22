package com.gmail.mooman219.layout;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.handler.database.UploadReason;
import com.mongodb.DBObject;

/*
 * PlayerData is save-able into the database and has the appropriate methods
 *   to save.
 */
public abstract class PlayerData extends PlayerLive {
    public PlayerData(CDPlayer player) {
        super(player);
    }

    public abstract String getTag();

    public abstract void sync(DBObject rank);

    public abstract DBObject getTemplate(UploadReason reason);
}
