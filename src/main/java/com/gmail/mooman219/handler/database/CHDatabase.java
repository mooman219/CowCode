package com.gmail.mooman219.handler.database;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bson.types.ObjectId;
import org.bukkit.Bukkit;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.bull.PlayerShutdownType;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.event.CEventFactory;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.CowHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class CHDatabase implements CowHandler {
    public final static String cast = "[CC][Database] ";

    public static Manager manager;
    private DB database;
    private DBCollection c_Users;
    private MongoClient client;

    public CHDatabase() {}

    @Override
    public String getName() {
        return "Database";
    }

    @Override
    public void onEnable() {
        manager = new Manager();
        try {
            client = new MongoClient(ConfigGlobal.hostname, ConfigGlobal.portnmbr);
            database = client.getDB("cowcode");
            if(!database.authenticate(ConfigGlobal.username, ConfigGlobal.password.toCharArray())) {
                throw new IllegalArgumentException("Unable to authenticate to database.");
            }
            c_Users = database.getCollection("data_users");
        } catch(Exception e) {
            e.printStackTrace();
            Loader.warning(cast + "Unable to connect to database");
            Bukkit.shutdown();
        }
        Loader.info(cast + "Currently" + (CHDatabase.manager.isConnected() ? " " : " not ") + "connected to database.");
    }

    @Override
    public void onDisable() {
        client.close();
    }

    public class Manager {
        public boolean isConnected() {
            try {
                database.command(new BasicDBObject("ping", "1"));
            } catch (MongoException e) {
                return false;
            }
            return true;
        }

        public void uploadPlayer(final CDPlayer player, final UploadReason reason, final boolean shouldRemove, final boolean runAsync) {
            final Runnable task = new Runnable() {
                @Override
                public void run() {
                    if(shouldRemove) {
                        CEventFactory.callDataRemovalEvent(runAsync || !Bukkit.isPrimaryThread(), player);
                        player.shutdown(PlayerShutdownType.POST_REMOVAL);
                    }
                    DBObject playerObject = player.getTemplate(reason);
                    c_Users.update(new BasicDBObject("_id", player.id), new BasicDBObject("$set", playerObject));
                    Loader.info(cast + "[UP] ["+reason.name()+"] : " + player.username);
                }
            };
            if(runAsync) {
                CHTask.manager.runPlugin(task);
            } else {
                task.run();
            }
        }

        public CDPlayer downloadPlayer(final String username, final DownloadReason reason) {
            // Create a downloader
            PlayerDownloader downloader = new PlayerDownloader(username, reason);
            // Run the downloader on the plugin's thread pool
            Future<CDPlayer> future = CHTask.manager.runPlugin(downloader);
            try {
                // Wait for the download, if it is taking too long, then just stop
                //  and return null. Anything that uses downloadPlayer should be r-
                // -eady to handle a null player
                return future.get(ConfigGlobal.downloadTimeout, TimeUnit.SECONDS);
            } catch(TimeoutException e) {
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    private class PlayerDownloader implements Callable<CDPlayer> {
        public final String username;
        public final DownloadReason reason;

        public PlayerDownloader(final String username, final DownloadReason reason) {
            this.username = username;
            this.reason = reason;
        }

        @Override
        public CDPlayer call() {
            try {
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
                        c_Users.insert(new BasicDBObject("username", username).append("usernamelowercase", username.toLowerCase()));
                        playerObject = c_Users.findOne(new BasicDBObject("username", username));
                        playerData = new CDPlayer((ObjectId) playerObject.get("_id"), username);
                        CHDatabase.manager.uploadPlayer(playerData, UploadReason.SAVE, false, false);
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
            } catch(Exception e) {
                Loader.warning("Something has gone wrong during " + username + "'s download request.");
                Loader.warning(cast + "Currently" + (CHDatabase.manager.isConnected() ? " " : " not ") + "connected to database.");
                e.printStackTrace();
            }
            return null;
        }
    }
}
