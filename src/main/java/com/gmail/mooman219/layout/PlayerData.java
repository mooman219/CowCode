package com.gmail.mooman219.layout;

import com.gmail.mooman219.handler.database.UploadReason;
import com.mongodb.DBObject;

public interface PlayerData {
    public String getTag();

    public void sync(DBObject rank);

    public DBObject getTemplate(UploadReason reason);
}
