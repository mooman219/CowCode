package com.gmail.mooman219.frame.database.mongo;

import com.gmail.mooman219.core.Loader;
import com.mongodb.DB;
import com.mongodb.Mongo;

public class MongoConnection {
    private Mongo connection;
    private String ip;
    private int port;
    private String username;
    private String password;

    /**
     * Create a new MongoConnection
     */
    public MongoConnection(String ip, int port, String user, String pass) {
        this.ip = ip;
        this.port = port;
        this.username = user;
        this.password = pass;
    }
    
    /*
     * Connects to the Mongo server
     */
    public boolean connect() {
        try {
            connection = new Mongo(ip, port);
            if(connection != null) {
                Loader.info(Loader.cast + "Connected to MongoDB");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Loader.warning(Loader.cast + "Could not connect to MongoDB on " + ip + ":" + port);
        return false;
    }

    /**
     * Returns the instance of the DB
     */
    public Mongo getInstance() {
        return connection;
    }

    /**
     * Returns an authenticated DB
     */
    public DB getDatabase(String name) {
        try {
            DB db = connection.getDB(name);
            db.authenticate(username, password.toCharArray());
            return db;
        } catch(Exception e) {
            e.printStackTrace();
        }
        Loader.warning(Loader.cast + "Could not get datbase: " + name);
        return null;
    }
}