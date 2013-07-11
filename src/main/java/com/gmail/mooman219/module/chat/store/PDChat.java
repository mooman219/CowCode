package com.gmail.mooman219.module.chat.store;

import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.layout.MongoData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDChat implements MongoData {
    public long mutedUntil = 0l;

    @Override
    public String getTag() {
        return "chat";
    }

    @Override
    public void sync(DBObject chat) {
        this.mutedUntil = MongoHelper.getValue(chat, "muteduntil", mutedUntil);
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".mutedUntil", mutedUntil);
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }
}
