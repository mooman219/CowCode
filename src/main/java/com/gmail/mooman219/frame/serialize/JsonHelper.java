package com.gmail.mooman219.frame.serialize;

import com.gmail.mooman219.frame.serialize.json.BasicVectorDouble;
import com.gmail.mooman219.frame.serialize.json.BasicVectorInteger;
import com.gmail.mooman219.module.region.store.RegionCombatType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {
    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
        .registerTypeAdapter(BasicVectorDouble.class, BasicVectorDouble.getAdapter())
        .registerTypeAdapter(BasicVectorInteger.class, BasicVectorInteger.getAdapter())
        .registerTypeAdapter(RegionCombatType.class, RegionCombatType.getAdapter());
    }

    public static Gson getGson() {
        return getGsonBuilder().create();
    }
}
