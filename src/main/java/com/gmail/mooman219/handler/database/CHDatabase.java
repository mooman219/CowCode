package com.gmail.mooman219.handler.database;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bukkit.Bukkit;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.bull.PlayerShutdownType;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.frame.CEventFactory;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.CowHandler;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;

public class CHDatabase implements CowHandler {
    public final static String cast = "[Database] ";

    public static Manager manager;
    private DB database;
    private DBCollection usersCollection;
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
            client = new MongoClient(ConfigGlobal.handler.database.hostname, ConfigGlobal.handler.database.portnmbr);
            database = client.getDB("cowcode");
            if(!database.authenticate(ConfigGlobal.handler.database.username, ConfigGlobal.handler.database.password.toCharArray())) {
                throw new IllegalArgumentException("Unable to authenticate to database.");
            }
            usersCollection = database.getCollection("data_users");
        } catch(Exception e) {
            Loader.warning(cast + "Unable to connect to database");
            e.printStackTrace();
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
                    WriteResult result = usersCollection.update(new BasicDBObject("username", player.getUsername()), new BasicDBObject("$set", playerObject));
                    if(result.getError() != null) {
                        Loader.warning(cast + "Mongo Error");
                        Loader.warning(cast + result.getError());
                    }
                    Loader.info(cast + "[UP] ["+reason.name()+"] : " + player.getUsername());
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
                return future.get(ConfigGlobal.handler.database.downloadTimeout, TimeUnit.SECONDS);
            } catch(TimeoutException e) {
            } catch(Exception e) {
                Loader.warning(cast + "Currently" + (isConnected() ? " " : " not ") + "connected to database.");
                e.printStackTrace();
            }
            return null;
        }

        private DBObject downloadPlayerObject(final String username, boolean caseSensitive) {
            if(caseSensitive) {
                return usersCollection.findOne(new BasicDBObject("username", username));
            } else {
                return usersCollection.findOne(new BasicDBObject("usernamelowercase", username.toLowerCase()));
            }
        }

        private void createPlayerObject(final String username) {
            usersCollection.insert(
                    new BasicDBObject()
                    .append("username", username)
                    .append("usernamelowercase", username.toLowerCase())
                    );
        }
    }

    private class PlayerDownloader implements Callable<CDPlayer> {
        public final String username;
        public final DownloadReason reason;

        public PlayerDownloader(String username, DownloadReason reason) {
            this.username = username;
            this.reason = reason;
        }

        @Override
        public CDPlayer call() {
            try {
                CDPlayer player;
                DBObject playerObject;
                switch(reason) {
                case LOGIN:
                    player = new CDPlayer(username);
                    playerObject = CHDatabase.manager.downloadPlayerObject(username, true);
                    Loader.info(cast + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                    if(playerObject == null) {
                        CHDatabase.manager.createPlayerObject(username);
                        CHDatabase.manager.uploadPlayer(player, UploadReason.SAVE, false, false);
                    } else {
                        player.sync(playerObject);
                    }
                    return player;
                case QUERY:
                    playerObject = CHDatabase.manager.downloadPlayerObject(username, false);
                    Loader.info(cast + "[DOWN] ["+reason.name()+"] [" + (playerObject != null ? "FOUND" : "NULL") + "] : " + username);
                    if(playerObject != null) {
                        player = new CDPlayer(username);
                        player.sync(playerObject);
                        return player;
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
