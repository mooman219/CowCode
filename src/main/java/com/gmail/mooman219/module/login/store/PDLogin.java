package com.gmail.mooman219.module.login.store;

import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDLogin implements PlayerData {
    public long lastlogin = 0l;
    public long firstlogin = 0l;
    public long timeplayed = 0l;
    public boolean isOnline = false;
    public String lastKnownIP = "0.0.0.0";

    @Override
    public String getTag() {
        return "login";
    }

    @Override
    public void sync(DBObject login) {
        this.firstlogin = MongoHelper.getValue(login, "firstlogin", firstlogin);
        this.lastlogin = MongoHelper.getValue(login, "lastlogin", lastlogin);
        this.timeplayed = MongoHelper.getValue(login, "timeplayed", timeplayed);
        this.isOnline = MongoHelper.getValue(login, "online", isOnline);
        this.lastKnownIP = MongoHelper.getValue(login, "lastknownip", lastKnownIP);
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(getTag(), "firstlogin", firstlogin) + "," +
                    TextHelper.buildQuery(getTag(), "lastlogin", lastlogin) + "," +
                    TextHelper.buildQuery(getTag(), "timeplayed", timeplayed) + "," +
                    TextHelper.buildQuery(getTag(), "online", isOnline) + "," +
                    TextHelper.buildQuery(getTag(), "lastknownip", lastKnownIP) +
                    "}");
        case STATUS:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(getTag(), "lastlogin", lastlogin) + "," +
                    TextHelper.buildQuery(getTag(), "online", isOnline) +
                    "}");
        default:
            return new BasicDBObject();
        }
    }
}
