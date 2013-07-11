package com.gmail.mooman219.module.login.store;

import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.layout.MongoData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDLogin implements MongoData {
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
            return new BasicDBObject()
            .append(getTag() + ".firstlogin", firstlogin)
            .append(getTag() + ".lastlogin", lastlogin)
            .append(getTag() + ".timeplayed", timeplayed)
            .append(getTag() + ".online", isOnline)
            .append(getTag() + ".lastknownip", lastKnownIP);
        case STATUS:
            return new BasicDBObject()
            .append(getTag() + ".lastlogin", lastlogin)
            .append(getTag() + ".online", isOnline);
        default:
            return new BasicDBObject();
        }
    }
}
