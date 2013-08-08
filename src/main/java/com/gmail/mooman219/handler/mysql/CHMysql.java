package com.gmail.mooman219.handler.mysql;

import java.sql.SQLException;

import org.bukkit.Bukkit;

import com.gmail.mooman219.core.Loader;
import com.gmail.mooman219.handler.mysql.store.StoreMysql;
import com.gmail.mooman219.layout.CowHandler;
import com.gmail.mooman219.layout.HandlerType;
import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class CHMysql extends CowHandler {
    private static final HandlerType type = HandlerType.MYSQL;
    private static Manager manager;

    public StoreMysql storeMysql;
    public BoneCPConfig config;
    public BoneCP connectionPool;

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
        storeMysql = new StoreMysql();
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
        public void connect() {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                config = new BoneCPConfig();
                config.setJdbcUrl("jdbc:mysql://" + StoreMysql.getData().serverAddress + ":" + StoreMysql.getData().serverPort);
                config.setUser(StoreMysql.getData().userName);
                config.setPassword(StoreMysql.getData().userPassword);
                connectionPool = new BoneCP(config);
                Loader.info(getCast() + "mySQL connected");
                return;
            } catch(SQLException e) {
                printErrors(e);
            } catch(Exception e) {
                e.printStackTrace();
            }
            Loader.warning(getCast() + "mySQL failure to connect");
            Bukkit.shutdown();
        }

        public void close() {
            if(connectionPool != null) {
                connectionPool.shutdown();
            }
        }

        public void printErrors(SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
            System.out.println("SQLState: " + exception.getSQLState());
            System.out.println("VendorError: " + exception.getErrorCode());
        }
    }
}
