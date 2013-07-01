package com.gmail.mooman219.frame;

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
        return MathHelper.toLong(document.get(query));
    }

    public static int getValue(DBObject document, String query, int defaultValue) {
        return MathHelper.toInt(document.get(query));
    }
}