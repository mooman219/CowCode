package com.gmail.mooman219.module.chat.store;

import java.lang.ref.SoftReference;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.database.MongoHelper;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
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
    public void sync(DownloadReason reason, DBObject chat) {
        switch(reason) {
        case LOGIN:
        case QUERY:
        default:
            this.mutedUntil = MongoHelper.getValue(chat, "muteduntil", mutedUntil);
            break;
        }
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".muteduntil", mutedUntil);
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    private SoftReference<CDPlayer> softLastMessaged;
    private long lastGlobalChat;

    public CDPlayer getLastMessaged() {
        if (softLastMessaged == null || softLastMessaged.get() == null) {
            return null;
        }
        return softLastMessaged.get();
    }

    public long getLastGlobalChat() {
        return lastGlobalChat;
    }

    public void setLastMessaged(CDPlayer lastMessaged) {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
        }
        softLastMessaged = new SoftReference<CDPlayer>(lastMessaged);
    }

    public void setLastGlobalChat(long time) {
        lastGlobalChat = time;
    }

    @Override
    public void create() {
        softLastMessaged = null;
        lastGlobalChat = 0l;
    }

    @Override
    public void destroy() {
        if(softLastMessaged != null) {
            softLastMessaged.clear();
            softLastMessaged = null;
        }
        lastGlobalChat = 0l;
    }
}
