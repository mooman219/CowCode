package com.gmail.mooman219.frame;

import java.lang.reflect.Field;

public class ReflectHelper {
    public static <T> Object getField(T object, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void setField(T object, Object value, String fieldName) {
        try {
            Field field = object.getClass().getDeclaredField("s");
            field.setAccessible(true);
            field.set(object, value);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
