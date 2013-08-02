package com.gmail.mooman219.frame;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class ReflectHelper {
    public static Field[] getFields(Class<?> clazz) {
        Field[] fields = null;
        try {
            fields = clazz.getDeclaredFields();
            for(Field field : fields) {
                field.setAccessible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fields;
    }


    public static Field getField(Class<?> clazz, String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return field;
    }

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
