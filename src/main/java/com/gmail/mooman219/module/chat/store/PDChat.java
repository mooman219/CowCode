package com.gmail.mooman219.module.chat.store;

import com.gmail.mooman219.frame.database.mongo.DatabaseData;
import com.gmail.mooman219.frame.database.mongo.MongoHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.frame.text.TextHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDChat implements DatabaseData {
    public final String tag = "chat";

    public long mutedUntil = 0l;

    public void sync(DBObject chat) {
        this.mutedUntil = MongoHelper.getValue(chat, "muteduntil", mutedUntil);
    }

    public DBObject getTemplate(UploadType uploadType) {
        switch(uploadType) {
        case CREATE:
        case NORMAL:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(tag, "mutedUntil", mutedUntil) +
                    "}");
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    public String getTag() {
        return tag;
    }
}
