package com.gmail.mooman219.module.chat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDChat extends PlayerData {
    public PDChat(CDPlayer player) {
        super(player, "chat");
    }

    /**
     * Offline
     */

    public long mutedUntil = 0l;

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

    /**
     * Live
     */

    private SoftReference<CDPlayer> softLastMessaged;
    public long lastGlobalChat;

    public CDPlayer getLastMessaged() {
        if (softLastMessaged == null || softLastMessaged.get() == null) {
            return null;
        }
        return softLastMessaged.get();
    }

    public void setLastMessaged(CDPlayer lastMessaged) {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
        }
        softLastMessaged = new SoftReference<CDPlayer>(lastMessaged);
    }

    @Override
    public void create() {
        softLastMessaged = null;
        lastGlobalChat = 0l;
    }

    @Override
    public void destroy() {
        softLastMessaged.clear();
        softLastMessaged = null;
        lastGlobalChat = 0l;
    }
}
