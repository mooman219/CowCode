package com.gmail.mooman219.handler.database;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bukkit.Bukkit;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.config.store.ConfigGlobal;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.layout.HandlerType;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;

public class CHDatabase extends CowHandler {
    private static final HandlerType type = HandlerType.DATABASE;
    private static Manager manager;

    private DB database;
    private DBCollection usersCollection;
    private MongoClient client;

    public CHDatabase(Loader plugin) {
        super(plugin);
    }

    @Override
    public HandlerType getType() {
        return type;
    }

    public static String getName() {
        return type.getName();
    }

    public static String getCast() {
        return type.getCast();
    }

    public static String getDirectory() {
        return type.getDirectory();
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
            Loader.warning(getCast() + "Unable to connect to database");
            e.printStackTrace();
            Bukkit.shutdown();
        }
        Loader.info(getCast() + "Currently" + (CHDatabase.manager.isConnected() ? " " : " not ") + "connected to database.");
    }

    @Override
    public void onDisable() {
        client.close();
    }

    public static Manager getManager() {
        return manager;
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

        public void uploadPlayer(CDPlayer player, UploadReason reason, boolean shouldRemove, boolean runAsync) {
            UploadRequest request = new UploadRequest(player, reason, shouldRemove, runAsync);
            if(runAsync) {
                CHTask.getManager().runPlugin(request);
            } else {
                request.run();
            }
        }

        public CDPlayer downloadPlayer(final String username, final DownloadReason reason) {
            PlayerDownloader downloader = new PlayerDownloader(username, reason);
            Future<CDPlayer> future = CHTask.getManager().runPlugin(downloader);
            try {
                return future.get(ConfigGlobal.handler.database.downloadTimeout, TimeUnit.SECONDS);
            } catch(TimeoutException e) {
            } catch(Exception e) {
                Loader.warning(getCast() + "Currently" + (isConnected() ? " " : " not ") + "connected to database.");
                e.printStackTrace();
            }
            return null;
        }

        protected DBObject downloadPlayerObject(final String username, boolean caseSensitive) {
            if(caseSensitive) {
                return usersCollection.findOne(new BasicDBObject("username", username));
            } else {
                return usersCollection.findOne(new BasicDBObject("usernamelowercase", username.toLowerCase()));
            }
        }

        protected void createPlayerObject(final String username) {
            usersCollection.insert(
                    new BasicDBObject()
                    .append("username", username)
                    .append("usernamelowercase", username.toLowerCase())
                    );
        }

        protected DBCollection getUsersCollection() {
            return usersCollection;
        }
    }
}
