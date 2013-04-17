package com.gmail.mooman219.module.service.store;

import com.gmail.mooman219.frame.database.mongo.MongoHelper;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.frame.rank.Rank;
import com.gmail.mooman219.frame.text.TextHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

public class PDService {
    public final String tag = "service";

    public Rank rank = Rank.REGULAR;
    public int donorLevel = 0;
    public int staffLevel = 0;

    public void sync(DBObject rank) {
        this.donorLevel = MongoHelper.getValue(rank, "donorlevel", donorLevel);
        this.staffLevel = MongoHelper.getValue(rank, "stafflevel", staffLevel);
        this.rank = Rank.getRank(this.donorLevel, this.staffLevel);
    }

    public DBObject getTemplate(UploadType uploadType) {
        switch(uploadType) {
        case CREATE:
            return (DBObject) JSON.parse("{" +
                    TextHelper.buildQuery(tag, "donorlevel", donorLevel) + "," +
                    TextHelper.buildQuery(tag, "stafflevel", staffLevel) +
                    "}");
        case NORMAL:
        case STATUS:
        default:
            return new BasicDBObject();
        }
    }

    public String getTag() {
        return tag;
    }
}
