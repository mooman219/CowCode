package com.gmail.mooman219.handler.database;

import org.bson.types.ObjectId;
import org.bukkit.Bukkit;

import com.gmail.mooman219.core.CowHandler;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.event.CEventFactory;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.handler.task.PluginThread;
import com.gmail.mooman219.module.CDPlayer;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class CHDatabase implements CowHandler {
    public final static String cast = "[CC][H][Database] ";

    public static Manager manager;
    private DB database;
    //private DBCollection c_Server;
    private DBCollection c_Users;
    private MongoClient client;

    @Override
    public void onEnable() {
        manager = new Manager();
        try {
            client = new MongoClient(ConfigGlobal.hostname, ConfigGlobal.portnmbr);
            database = client.getDB("cowcode");
            if(!database.authenticate(ConfigGlobal.username, ConfigGlobal.password.toCharArray())) {
                throw new IllegalArgumentException("Unable to authenticate to database.");
            }
            //c_Server = database.getCollection("data_server");
            c_Users = database.getCollection("data_users");
        } catch(Exception e) {
            e.printStackTrace();
            Loader.warning(cast + "Unable to connect to database");
            Bukkit.shutdown();
        }
    }

    @Override
    public void onDisable() {
        client.close();
    }

    public class Manager {
        public void uploadPlayer(final CDPlayer playerData, final UploadReason reason, final UploadThread thread) {
            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    if(thread.remove) {
                        CEventFactory.callDataRemovalEvent(thread.async || thread.removeAsync, playerData.player);
                    }
                    DBObject playerObject = playerData.getTemplate(reason);
                    c_Users.update(new BasicDBObject("_id", playerData.id)
                    , new BasicDBObject("$set", playerObject));
                    Loader.info(cast + "[UP] ["+reason.name()+"] : " + playerData.username);
                }
            };
            if(thread.async) {
                CHTask.manager.runPlugin(task, PluginThread.ASYNC);
            } else {
                task.run();
            }
        }

        public CDPlayer downloadPlayer(final String username, final DownloadReason reason) {
            CDPlayer playerData;
            DBObject playerObject;
            switch(reason) {
            case LOGIN:
                playerObject = c_Users.findOne(new BasicDBObject("username", username));
                Loader.info(cast + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                if(playerObject != null) {
                    playerData = new CDPlayer((ObjectId) playerObject.get("_id"), username);
                    playerData.sync(playerObject);
                    return playerData;
                } else {
                    c_Users.insert(new BasicDBObject("username", username)
                    .append("usernamelowercase", username.toLowerCase()));
                    playerObject = c_Users.findOne(new BasicDBObject("username", username));
                    playerData = new CDPlayer((ObjectId) playerObject.get("_id"), username);
                    uploadPlayer(playerData, UploadReason.CREATION, UploadThread.ASYNC);
                    return playerData;
                }
            case QUERY:
                playerObject = c_Users.findOne(new BasicDBObject("usernamelowercase", username.toLowerCase()));
                Loader.info(cast + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                if(playerObject != null) {
                    playerData = new CDPlayer((ObjectId) playerObject.get("_id"), username);
                    playerData.sync(playerObject);
                    return playerData;
                } else {
                    return null;
                }
            default:
                return null;
            }
        }
    }
}
