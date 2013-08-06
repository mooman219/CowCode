package com.gmail.mooman219.module.graveyard.store;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.ConfigJson;
import com.google.gson.Gson;

public class StoreGraveyard extends ConfigJson {
    private static ArrayList<BasicGraveyard> graveyards = new ArrayList<BasicGraveyard>();

    public StoreGraveyard(String cast, String directory) {
        super(cast, directory, "graveyards", "yml");
    }

    @Override
    public void onSave(FileWriter writer) {
        getGson().toJson(this, writer);
    }

    @Override
    public void onLoad(FileReader reader) {
        getGson().fromJson(reader, StoreGraveyard.class);
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .setPrettyPrinting()
        .create();
    }

    public static ArrayList<BasicGraveyard> getGraveyards() {
        return graveyards;
    }
}
