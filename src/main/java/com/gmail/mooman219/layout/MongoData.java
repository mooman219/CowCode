package com.gmail.mooman219.layout;

import com.gmail.mooman219.handler.database.UploadReason;
import com.mongodb.DBObject;

/*
 * PlayerData is save-able into the database and has the appropriate methods
 *   to save.
 */
public interface MongoData {
    public String getTag();

    public void sync(DBObject rank);

    public DBObject getTemplate(UploadReason reason);
}
