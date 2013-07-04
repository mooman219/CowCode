package com.gmail.mooman219.module.region.store;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.HashMap;

import com.gmail.mooman219.frame.file.ConfigJson;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicChunkLocation;
import com.google.gson.Gson;

public class StoreChunk extends ConfigJson {
    private static HashMap<BasicChunkLocation, String> chunks = new HashMap<BasicChunkLocation, String>();

    public StoreChunk(String directory) {
        super(directory, "chunks", "data");
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
        getGson().fromJson(reader, StoreChunk.class);
    }

    @Override
    public void onSave(FileWriter writer) {
        getGson().toJson(this, writer);
    }

    public static HashMap<BasicChunkLocation, String> getChunks() {
        return chunks;
    }
}