package com.gmail.mooman219.module.service.store;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.MongoHelper;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.gmail.mooman219.handler.database.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDService extends PlayerData {
    public Rank rank = Rank.REGULAR;
    public int donorLevel = 0;
    public int staffLevel = 0;

    public PDService(CDPlayer player) {
        super(player);
    }

    @Override
    public String getTag() {
        return "service";
    }

    @Override
    public void sync(DBObject rank) {
        this.donorLevel = MongoHelper.getValue(rank, "donorlevel", donorLevel);
        this.staffLevel = MongoHelper.getValue(rank, "stafflevel", staffLevel);
        this.rank = Rank.getRank(this.donorLevel, this.staffLevel);
    }

    @Override
    public DBObject getTemplate(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(getTag(), "donorlevel", donorLevel) + "," +
                    TextHelper.buildQuery(getTag(), "stafflevel", staffLevel) +
                    "}");
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }
}
