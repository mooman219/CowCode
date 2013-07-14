package com.gmail.mooman219.frame;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectHelper {
    public static <T> Object get(Class<?> clazz, T object, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(object);
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> void set(Class<?> clazz, T object, Object value, String fieldName) {
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(object, value);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void setStatic(Class<?> clazz, Object value, String fieldName) {
        try {
            Field field = clazz.getField(fieldName);
            field.setAccessible(true);
            field.set(null, value);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void setFinalStatic(Class<?> clazz, Object value, String fieldName) {
        try {
            Field field = clazz.getField(fieldName);
            field.setAccessible(true);
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(null, value);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
