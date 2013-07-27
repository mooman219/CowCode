package com.gmail.mooman219.frame.database;

import com.gmail.mooman219.frame.math.NumberHelper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;

public class MongoHelper {
    public static <T> WriteResult set(DBCollection collection, BasicDBObject object, String key, T value) {
        BasicDBObject set = new BasicDBObject();
        set.put("$set", new BasicDBObject(key, value));
        return collection.update(object, set);
    }

    public static boolean doesExist(DBCollection collection, BasicDBObject object) {
        return collection.findOne(object) != null;
    }

    public static DBObject get(DBCollection collection, BasicDBObject object) {
        return collection.findOne(object);
    }

    public static <T> T getValue(DBObject document, String query, T defaultValue) {
        try {
            T object = (T) document.get(query);
            return object == null ? defaultValue : object;
        } catch(Exception e) {}
        return defaultValue;
    }

    public static long getValue(DBObject document, String query, long defaultValue) {
        Object object = document.get(query);
        return object == null ? defaultValue : NumberHelper.toLong(object, defaultValue);
    }

    public static double getValue(DBObject document, String query, double defaultValue) {
        Object object = document.get(query);
        return object == null ? defaultValue : NumberHelper.toDouble(object, defaultValue);
    }

    public static float getValue(DBObject document, String query, float defaultValue) {
        Object object = document.get(query);
        return object == null ? defaultValue : NumberHelper.toFloat(object, defaultValue);
    }

    public static int getValue(DBObject document, String query, int defaultValue) {
        Object object = document.get(query);
        return object == null ? defaultValue : NumberHelper.toInt(object, defaultValue);
    }

    public static short getValue(DBObject document, String query, short defaultValue) {
        Object object = document.get(query);
        return object == null ? defaultValue : NumberHelper.toShort(object, defaultValue);
    }

    public static byte getValue(DBObject document, String query, byte defaultValue) {
        Object object = document.get(query);
        return object == null ? defaultValue : NumberHelper.toByte(object, defaultValue);
    }
}