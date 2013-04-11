package com.gmail.mooman219.frame.database.mongo;

import com.mongodb.DBObject;

public interface DatabaseData {
    public void sync(DBObject playerObject);

    public DBObject getTemplate(UploadType uploadType);

    public String getTag();
}
