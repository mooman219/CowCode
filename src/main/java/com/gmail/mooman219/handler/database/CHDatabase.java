package com.gmail.mooman219.handler.database;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.bukkit.Bukkit;

import com.gmail.mooman219.bull.CDPlayer;
import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.database.store.ConfigDatabase;
import com.gmail.mooman219.handler.database.type.DownloadReason;
import com.gmail.mooman219.handler.database.type.UploadReason;
import com.gmail.mooman219.handler.task.CHTask;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.layout.HandlerType;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;

public class CHDatabase extends CowHandler {
    private static final HandlerType type = HandlerType.DATABASE;
    private static Manager manager;

    public ConfigDatabase configDatabase;
    private DB database;
    private DBCollection usersCollection;
    private MongoClient client;

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
        configDatabase = new ConfigDatabase();
        manager = new Manager();
        manager.connect();
    }

    @Override
    public void onDisable() {
        manager.close();
    }

    public static Manager getManager() {
        return manager;
    }

    public class Manager {
        public boolean connect() {
            Loader.info(getCast() + "Connecting to the database...");
            try {
                ServerAddress address = new ServerAddress(ConfigDatabase.getData().hostname, ConfigDatabase.getData().portnmbr);
                client = new MongoClient(address);
                database = client.getDB("cowcode");
                if(!database.authenticate(ConfigDatabase.getData().username, ConfigDatabase.getData().password.toCharArray())) {
                    throw new IllegalArgumentException("Unable to authenticate to database.");
                }
                usersCollection = database.getCollection("data_users");
            } catch(Exception e) {
                e.printStackTrace();
                Loader.warning(getCast() + "Error while connecting to the database");
                Bukkit.shutdown();
            }
            Loader.info(getCast() + "Currently" + (CHDatabase.manager.isConnected() ? " " : " not ") + "connected to database.");
            return CHDatabase.manager.isConnected();
        }

        public void close() {
            client.close();
        }

        public boolean isConnected() {
            try {
                if(database != null) {
                    database.command(new BasicDBObject("ping", "1"));
                    return true;
                }
            } catch (Exception e) {}
            return false;
        }

        public void uploadPlayer(CDPlayer player, UploadReason reason, boolean shouldRemove, boolean runAsync) {
            UploadRequest request = new UploadRequest(player, reason, shouldRemove);
            if(runAsync) {
                CHTask.getManager().runPlugin(request);
            } else {
                request.run();
            }
        }

        /**
         * This method will block while getting the data from the database.
         */
        public CDPlayer downloadPlayer(String username, DownloadReason reason) {
            PlayerDownloader downloader = new PlayerDownloader(username, reason);
            Future<CDPlayer> future = CHTask.getManager().runPlugin(downloader);
            try {
                return future.get(ConfigDatabase.getData().downloadTimeout, TimeUnit.SECONDS);
            } catch(TimeoutException e) {
            } catch(Exception e) {
                Loader.warning(getCast() + "Currently" + (isConnected() ? " " : " not ") + "connected to database.");
                e.printStackTrace();
            }
            return null;
        }

        /**
         * Returns the users collection. All user data is stored here.
         */
        protected DBCollection getUsers() {
            return usersCollection;
        }
    }
}
