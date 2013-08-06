package com.gmail.mooman219.module.region.store;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.UUID;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.ConfigJson;
import com.gmail.mooman219.module.region.type.RegionCombatType;
import com.google.gson.Gson;

public class StoreRegion extends ConfigJson {
    private static transient final BasicRegion globalInfo =
            new BasicRegion("283453ad-094b-92b7-b191-a07bff41d667", "global", "Global")
            .setDescription("No region exists here")
            .setCombatType(RegionCombatType.SAFE);
    private static HashMap<UUID, BasicRegion> regions = new HashMap<UUID, BasicRegion>();

    public StoreRegion(String cast, String directory) {
        super(cast, directory, "regions", "yml");
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .setPrettyPrinting()
        .create();
    }

    @Override
    public void onLoad(FileReader reader) {
        getGson().fromJson(reader, StoreRegion.class);
    }

    @Override
    public void onSave(FileWriter writer) {
        getGson().toJson(this, writer);
    }

    public static BasicRegion getGlobalInfo() {
        return globalInfo;
    }

    public static HashMap<UUID, BasicRegion> getRegions() {
        return regions;
    }
}