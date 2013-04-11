package com.gmail.mooman219.module.login;

import com.gmail.mooman219.frame.database.mongo.DatabaseData;
import com.gmail.mooman219.frame.database.mongo.MongoHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.frame.text.TextHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDLogin implements DatabaseData {
    public final String tag = "login";

    public long lastlogin = 0l;
    public long firstlogin = 0l;
    public long timeplayed = 0l;
    public boolean isOnline = false;
    public String lastKnownIP = "0.0.0.0";

    public void sync(DBObject login) {
        this.firstlogin = MongoHelper.getValue(login, "firstlogin", firstlogin);
        this.lastlogin = MongoHelper.getValue(login, "lastlogin", lastlogin);
        this.timeplayed = MongoHelper.getValue(login, "timeplayed", timeplayed);
        this.isOnline = MongoHelper.getValue(login, "online", isOnline);
        this.lastKnownIP = MongoHelper.getValue(login, "lastknownip", lastKnownIP);
    }

    public DBObject getTemplate(UploadType uploadType) {
        switch(uploadType) {
        case CREATE:
        case NORMAL:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(tag, "firstlogin", firstlogin) + "," +
                    TextHelper.buildQuery(tag, "lastlogin", lastlogin) + "," +
                    TextHelper.buildQuery(tag, "timeplayed", timeplayed) + "," +
                    TextHelper.buildQuery(tag, "online", isOnline) + "," +
                    TextHelper.buildQuery(tag, "lastknownip", lastKnownIP) +
                    "}");
        case STATUS:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(tag, "lastlogin", lastlogin) + "," +
                    TextHelper.buildQuery(tag, "online", isOnline) +
                    "}");
        default:
            return new BasicDBObject();
        }
    }

    public String getTag() {
        return tag;
    }
}
