package com.gmail.mooman219.frame.serialize;

import java.lang.reflect.Type;
import java.util.Collection;

import org.bukkit.Material;

import com.gmail.mooman219.frame.serialize.json.BasicVectorDouble;
import com.gmail.mooman219.frame.serialize.json.BasicVectorInteger;
import com.gmail.mooman219.frame.serialize.json.BukkitAdapters;
import com.gmail.mooman219.module.region.store.RegionCombatType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JsonHelper {
    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
        .registerTypeAdapter(BasicVectorDouble.class, BasicVectorDouble.getAdapter())
        .registerTypeAdapter(BasicVectorInteger.class, BasicVectorInteger.getAdapter())
        .registerTypeAdapter(RegionCombatType.class, RegionCombatType.getAdapter())
        .registerTypeAdapter(Material.class, BukkitAdapters.getMaterialAdapter());
    }

    public static Gson getGson() {
        return getGsonBuilder().create();
    }

    public static <T> Type getCollectionType(T type) {
        return new TypeToken<Collection<T>>(){}.getType();
    }

    public static String toJson(Object object) {
        return getGson().toJson(object);
    }
}
