package com.gmail.mooman219.module.chat.store;

import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDChat {
    public final String tag = "chat";

    public long mutedUntil = 0l;

    public void sync(DBObject chat) {
        this.mutedUntil = MongoHelper.getValue(chat, "muteduntil", mutedUntil);
    }

    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case CREATION:
        case SAVE:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(tag, "mutedUntil", mutedUntil) +
                    "}");
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }
}
