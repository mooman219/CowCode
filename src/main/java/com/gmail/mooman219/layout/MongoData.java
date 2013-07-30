package com.gmail.mooman219.layout;

import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.mongodb.DBObject;

/*
 * PlayerData is save-able into the database and has the appropriate methods
 *   to save.
 */
public interface MongoData {
    public String getTag();

    public void load(DownloadReason reason, DBObject object);

    public DBObject save(UploadReason reason);
}
