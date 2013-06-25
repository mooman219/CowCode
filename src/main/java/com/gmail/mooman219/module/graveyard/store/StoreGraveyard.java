package com.gmail.mooman219.module.graveyard.store;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.Gson;

public class StoreGraveyard extends ConfigJson {
    public static ArrayList<BasicGraveyard> graveyards = new ArrayList<BasicGraveyard>();

    public StoreGraveyard(String directory) {
        super(directory, "graveyards", "yml");
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
}
