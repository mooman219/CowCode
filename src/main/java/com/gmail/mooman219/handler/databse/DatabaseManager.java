
package com.gmail.mooman219.handler.databse;

import org.bson.types.ObjectId;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.database.mongo.DownloadType;
import com.gmail.mooman219.frame.database.mongo.MongoConnection;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.DLPlayer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

public class DatabaseManager {
    public static final String n_Database = "cowcode";
    public static final String n_Server = "data_server";
    public static final String n_Users = "data_users";

    public DB d_cowcode;

    public DBCollection c_Server;
    public DBCollection c_Users;
    public MongoConnection connection;

    public void start() {
        if(connection != null) {
            stop();
        }
        connection = new MongoConnection(ConfigGlobal.hostname, ConfigGlobal.portnmbr, ConfigGlobal.username, ConfigGlobal.password);
        connection.connect();
        d_cowcode = connection.getDatabase(n_Database);
        c_Server = d_cowcode.getCollection(n_Server);
        c_Users = d_cowcode.getCollection(n_Users);
    }

    public void stop() {
        d_cowcode = null;
        c_Server = null;
        c_Users = null;
        connection.getInstance().close();
        connection = null;
    }

    public void uploadServerStatus(boolean isOnline) {
        BasicDBObject locationDocument = new BasicDBObject("location", ConfigGlobal.server_loc);
        locationDocument.put("name", ConfigGlobal.server_id);
        if(c_Server.findOne(locationDocument) == null) {
            c_Server.insert(locationDocument);
        }
        c_Server.update(locationDocument, new BasicDBObject("$set", getCompleteServerTemplate(isOnline)));
    }

    private BasicDBObject getCompleteServerTemplate(boolean isOnline) {
        BasicDBObject serverDocument = new BasicDBObject();
        serverDocument.put("name", ConfigGlobal.server_id);
        serverDocument.put("location", ConfigGlobal.server_loc);
        serverDocument.put("online", isOnline);
        return serverDocument;
    }

    public DLPlayer downloadPlayerData(String username, DownloadType downloadType) {
        DBObject usernameQuery;
        DBObject playerObject;
        DLPlayer ret = null;
        switch(downloadType) {
        case QUERY:
            usernameQuery = new BasicDBObject("usernamelowercase", username.toLowerCase());
            break;
        case LOGIN:
        default:
            usernameQuery = new BasicDBObject("username", username);
            break;
        }
        playerObject = c_Users.findOne(usernameQuery);
        if(playerObject != null) {
            Loader.info(CHDatabase.cast + "[DOWN] ["+downloadType.name()+"] normal: " + username);
            ret = new DLPlayer((ObjectId) playerObject.get("_id"), username);
            ret.sync(playerObject);
        } else {
            switch(downloadType) {
            case LOGIN:
                Loader.info(CHDatabase.cast + "[DOWN] ["+downloadType.name()+"] missing: " + username);
                usernameQuery.put("usernamelowercase", username.toLowerCase());
                c_Users.insert(usernameQuery);
                ret = new DLPlayer((ObjectId) c_Users.findOne(usernameQuery).get("_id"), username);
                uploadPlayerData(ret, UploadType.CREATE);
                break;
            case QUERY:
            default:
                Loader.info(CHDatabase.cast + "[DOWN] ["+downloadType.name()+"] ignore: " + username);
                break;
            }
        }
        return ret;
    }

    public void uploadPlayerData(DLPlayer playerData, UploadType uploadType) {
        Loader.info(CHDatabase.cast + "[UP] ["+uploadType.name()+"] normal: " + playerData.username);
        c_Users.update(new BasicDBObject("_id", playerData.id), new BasicDBObject("$set", playerData.getTemplate(uploadType)));
    }
}