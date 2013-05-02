package com.gmail.mooman219.module.login.store;

import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDLogin {
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

    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case CREATION:
        case SAVE:
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
}
