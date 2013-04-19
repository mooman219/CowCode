package com.gmail.mooman219.old;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public abstract class CStorage<T> {
    protected HashMap<String, T> store = null;

    public T get(String key) {
        if(!isInitialized()) {
            return null;
        }
        return store.get(key);
    }

    public <E> E get(Class<E> type) {
        if(!isInitialized()) {
            return null;
        }
        if(type.isAnnotationPresent(DefaultTag.class)) {
            String key = type.getAnnotation(DefaultTag.class).key();
            if(store.containsKey(key)) {
                return (E) store.get(key);
            }
        }
        throw new IllegalArgumentException(type.getName() + " does not implement DefaultTag.");
    }

    public <E> E get(String key, E fallback) {
        if(!isInitialized()) {
            return null;
        }
        if(store.containsKey(key)) {
            return (E) store.get(key);
        }
        return fallback;
    }

    public <E> E get(String key, Class<E> type) {
        if(!isInitialized()) {
            return null;
        }
        if(store.containsKey(key)) {
            return (E) store.get(key);
        }
        return null;
    }

    public boolean set(T object) {
        if(object.getClass().isAnnotationPresent(DefaultTag.class)) {
            String key = object.getClass().getAnnotation(DefaultTag.class).key();
            return set(key, object);
        }
        throw new IllegalArgumentException(object.getClass().getName() + " does not implement DefaultTag.");
    }

    public boolean set(String key, T object) {
        initialize();
        boolean ret = store.containsKey(key);
        store.put(key, object);
        return ret;
    }

    public T remove(String key) {
        if(!isInitialized()) {
            return null;
        }
        return store.remove(key);
    }

    public boolean containsKey(String key) {
        if(!isInitialized()) {
            return false;
        }
        return store.containsKey(key);
    }

    public Set<String> keySet() {
        if(!isInitialized()) {
            return new HashSet<String>(0);
        }
        return store.keySet();
    }

    public Collection<T> values() {
        if(!isInitialized()) {
            return new ArrayList<T>(0);
        }
        return store.values();
    }

    public boolean initialize() {
        if(this.store == null) {
            this.store = new HashMap<String, T>();
            return true;
        } else {
            return false;
        }
    }

    public boolean isInitialized() {
        return this.store != null;
    }
}