package com.gmail.mooman219.frame.database.mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

public class MySQL {
    public final String DATABASE_PREFIX;
    public final String PREFIX;
    public Logger log;
    public boolean connected;
    public Connection connection;

    private String hostname = "localhost";
    private String portnmbr = "3306";
    private String username = "minecraft";
    private String password = "";
    private String database = "minecraft";

    public MySQL(Logger log, String prefix, String hostname, String portnmbr,
            String database, String username, String password) {

        this.log = log;
        this.PREFIX = prefix;
        this.DATABASE_PREFIX = "[MySQL] ";
        this.connected = false;
        this.connection = null;

        this.hostname = hostname;
        this.portnmbr = portnmbr;
        this.database = database;
        this.username = username;
        this.password = password;
    }

    public boolean checkConnection() {
        if (connection != null) {
            return true;
        }
        return false;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            writeError("Failed to close database connection: " + e.getMessage(), true);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private boolean initialize() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return true;
        } catch (ClassNotFoundException e) {
            writeError("Class Not Found Exception: " + e.getMessage() + ".", true);
            return false;
        }
    }

    public Connection open() {
        if (initialize()) {
            String url = "";
            try {
                url = "jdbc:mysql://" + hostname + ":" + portnmbr + "/" + database;
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                printErrors(e);
            }
        }
        return null;
    }

    public boolean write(String sql) {
        if (checkConnection()) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                statement.executeUpdate();
                return true;
            }
            catch (SQLException e) {
                printErrors(e);
                return false;
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        printErrors(e);
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public int update(String sql) {
        int ret = 0;
        if (checkConnection()) {
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement(sql);
                ret = statement.executeUpdate();
                return ret;
            } catch (SQLException e) {
                printErrors(e);
                return 0;
            } finally {
                if (statement != null) {
                    try {
                        statement.close();
                    } catch (SQLException e) {
                        printErrors(e);
                        return 0;
                    }
                }
            }
        }
        return ret;
    }

    public String getString(String sql) {
        ResultSet resultSet;
        String result = "";
        if (checkConnection()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    result = resultSet.getString(1);
                } else {
                    result = "fuckyou";
                }
                statement.close();
            } catch (SQLException ex) {
                printErrors(ex);
            }
        }
        return result;
    }

    public HashMap<Integer, ArrayList<String>> read(String sql) {
        ResultSet resultSet = null;
        HashMap<Integer, ArrayList<String>> rows = new HashMap<Integer, ArrayList<String>>();
        if (checkConnection()) {
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                resultSet = statement.executeQuery();
                while (resultSet.next()) {
                    ArrayList<String> column = new ArrayList<String>();
                    for (int i = 1; i <= resultSet.getMetaData().getColumnCount(); i++) {
                        column.add(resultSet.getString(i));
                    }
                    rows.put(resultSet.getRow(), column);
                }
                resultSet.close();
                statement.close();
            }catch (SQLException ex) {
                printErrors(ex);
            }
        }
        return rows;
    }

    private void writeError(String toWrite, boolean severe) {
        if (toWrite != null) {
            if (severe) {
                log.severe(PREFIX + DATABASE_PREFIX + toWrite);
            } else {
                log.warning(PREFIX + DATABASE_PREFIX + toWrite);
            }
        }
    }

    private void printErrors(SQLException e) {
        System.out.println("SQLException: " + e.getMessage());
        System.out.println("SQLState: " + e.getSQLState());
        System.out.println("VendorError: " + e.getErrorCode());
    }
}