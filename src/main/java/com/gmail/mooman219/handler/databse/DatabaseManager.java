
package com.gmail.mooman219.handler.databse;

import org.bson.types.ObjectId;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.database.mongo.MongoConnection;
import com.gmail.mooman219.frame.database.mongo.UploadType;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.service.CCService;
import com.gmail.mooman219.module.service.player.PlayerData;
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

    public PlayerData downloadPlayerData(String username, boolean create, boolean caseSensitive) {
        DBObject usernameQuery;
        if(caseSensitive) {
            usernameQuery = new BasicDBObject("username", username);
        } else {
            usernameQuery = new BasicDBObject("usernamelowercase", username.toLowerCase());
        }
        DBObject playerObject = c_Users.findOne(usernameQuery);
        PlayerData ret = null;
        if(playerObject != null) {
            Loader.info(CCService.cast + "[DOWN] normal: " + username);
            ret = new PlayerData((ObjectId) playerObject.get("_id"), username);
            ret.sync(playerObject);
        } else if(create && caseSensitive) {
            Loader.info(CCService.cast + "[DOWN] missing: " + username);
            usernameQuery.put("usernamelowercase", username.toLowerCase());
            c_Users.insert(usernameQuery);
            ret = new PlayerData((ObjectId) c_Users.findOne(usernameQuery).get("_id"), username);
            uploadPlayerData(ret, UploadType.CREATE);
        } else if(create && !caseSensitive) {
            throw new IllegalArgumentException("Username must be Case Sensitive to create it.");
        }
        return ret;
    }

    public void uploadPlayerData(PlayerData playerData, UploadType uploadType) {
        Loader.info(CCService.cast + "[UP] ["+uploadType.name()+"] normal: " + playerData.username);
        c_Users.update(new BasicDBObject("_id", playerData.id), new BasicDBObject("$set", playerData.getTemplate(uploadType)));
    }
}