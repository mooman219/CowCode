package com.gmail.mooman219.frame.database.mongo;

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
        T object = (T) document.get(query);
        return object == null ? defaultValue : object;
    }

    public static long getValue(DBObject document, String query, long defaultValue) {
        Object object = document.get(query);
        long ret = defaultValue;
        if(object != null) {
            if(object instanceof Long) {
                ret = (Long) object;
            } else {
                try {
                    ret = Long.parseLong(object.toString());
                } catch(Exception e) {}
            }
        }
        return ret;
    }

    public static int getValue(DBObject document, String query, int defaultValue) {
        Object object = document.get(query);
        int ret = defaultValue;
        if(object != null) {
            if(object instanceof Integer) {
                ret = (Integer) object;
            } else {
                try {
                    ret = Integer.parseInt(object.toString());
                } catch(Exception e) {}
            }
        }
        return ret;
    }
}