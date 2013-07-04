package com.gmail.mooman219.module.region.store;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.module.region.type.RegionCombatType;
import com.google.gson.Gson;

public class StoreRegion extends ConfigJson {
    private static transient final BasicRegion globalInfo =
            new BasicRegion("GLOBALREGIONUUID-90f5e0f50661c3951a2", "global", "Global")
            .setDescription("No region exists here")
            .setCombatType(RegionCombatType.SAFE);
    private static HashMap<String, BasicRegion> regions = new HashMap<String, BasicRegion>();

    public StoreRegion(String directory) {
        super(directory, "regions", "yml");
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

    public static HashMap<String, BasicRegion> getRegions() {
        return regions;
    }
}