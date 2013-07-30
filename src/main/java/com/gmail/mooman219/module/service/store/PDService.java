package com.gmail.mooman219.module.service.store;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.database.MongoHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDService extends PlayerData {
    public PDService(CDPlayer player) {
        super(player, "service");
    }

    /**
     * Offline
     */

    public Rank rank = Rank.REGULAR;
    public int donorLevel = 0;
    public int staffLevel = 0;

    @Override
    public void load(DownloadReason reason, DBObject rank) {
        switch(reason) {
        case LOGIN:
        case QUERY:
        default:
            this.donorLevel = MongoHelper.getValue(rank, "donorlevel", donorLevel);
            this.staffLevel = MongoHelper.getValue(rank, "stafflevel", staffLevel);
            this.rank = Rank.getRank(this.donorLevel, this.staffLevel);
            break;
        }
    }

    @Override
    public DBObject save(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".donorlevel", donorLevel)
            .append(getTag() + ".stafflevel", staffLevel);
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    // No live data
}
