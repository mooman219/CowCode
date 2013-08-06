package com.gmail.mooman219.module.region.store;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicChunkLocation;
import com.gmail.mooman219.frame.serialize.json.ConfigJson;
import com.google.gson.Gson;

public class StoreChunk extends ConfigJson {
    private static ArrayList<BasicChunkRegion> chunks = new ArrayList<BasicChunkRegion>();
    private static transient HashMap<BasicChunkLocation, UUID> chunkMap = new HashMap<BasicChunkLocation, UUID>();

    public StoreChunk(String cast, String directory) {
        super(cast, directory, "chunks", "data");
    }

    @Override
    public Gson getGson() {
        return JsonHelper.getGsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT)
        .create();
    }

    @Override
    public void onLoad(FileReader reader) {
        chunkMap.clear();
        getGson().fromJson(reader, StoreChunk.class);
        for(BasicChunkRegion chunk : chunks) {
            chunkMap.put(chunk.getChunk(), chunk.getUUID());
        }
    }

    @Override
    public void onSave(FileWriter writer) {
        chunks.clear();
        for(BasicChunkLocation chunk : chunkMap.keySet()) {
            chunks.add(new BasicChunkRegion(chunk, chunkMap.get(chunk)));
        }
        getGson().toJson(this, writer);
    }

    public static HashMap<BasicChunkLocation, UUID> getChunks() {
        return chunkMap;
    }
}