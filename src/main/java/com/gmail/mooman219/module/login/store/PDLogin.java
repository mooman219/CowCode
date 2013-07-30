package com.gmail.mooman219.module.login.store;

import java.util.ArrayList;

import org.bukkit.Location;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.frame.database.MongoHelper;
import com.gmail.mooman219.frame.serialize.json.BasicRichLocation;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.layout.PlayerData;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class PDLogin extends PlayerData {
    public PDLogin(CDPlayer player) {
        super(player, "login");
    }

    /**
     * Offline
     */

    public long lastlogin = 0l;
    public long firstlogin = 0l;
    public long timeplayed = 0l;
    public boolean isOnline = false;
    public ArrayList<String> knownIPs = new ArrayList<String>();
    public String lastKnownIP = "0.0.0.0";

    @Override
    public void load(DownloadReason reason, DBObject login) {
        switch(reason) {
        case LOGIN:
            setPosition(BasicRichLocation.fromString(MongoHelper.getValue(login, "position", "")));
        case QUERY:
            this.firstlogin = MongoHelper.getValue(login, "firstlogin", firstlogin);
            this.lastlogin = MongoHelper.getValue(login, "lastlogin", lastlogin);
            this.timeplayed = MongoHelper.getValue(login, "timeplayed", timeplayed);
            this.isOnline = MongoHelper.getValue(login, "online", isOnline);
            this.knownIPs = MongoHelper.getValue(login, "iplist", knownIPs);
            this.lastKnownIP = MongoHelper.getValue(login, "lastip", lastKnownIP);
        default:
            break;
        }
    }

    @Override
    public DBObject save(UploadReason reason) {
        switch(reason) {
        case SAVE:
            return new BasicDBObject()
            .append(getTag() + ".firstlogin", firstlogin)
            .append(getTag() + ".lastlogin", lastlogin)
            .append(getTag() + ".timeplayed", timeplayed)
            .append(getTag() + ".online", isOnline)
            .append(getTag() + ".iplist", knownIPs)
            .append(getTag() + ".lastip", lastKnownIP)
            .append(getTag() + ".position", position != null ? position.toString() : "");
        case STATUS:
            return new BasicDBObject()
            .append(getTag() + ".lastlogin", lastlogin)
            .append(getTag() + ".online", isOnline);
        default:
            return new BasicDBObject();
        }
    }

    /**
     * Live
     */

    private BasicRichLocation position;

    public Location getPosition() {
        return position != null ? position.toLocation() : null;
    }

    private void setPosition(BasicRichLocation position) {
        this.position = position;
    }

    public void setPosition(Location position) {
        this.position = new BasicRichLocation(position);
    }

    /**
     * No create this time around because of the special sync.
     * This in turn means we can process all the shit on a different thread.
     */
    @Override
    public void create() {}

    @Override
    public void destroy() {
        position = null;
    }
}
