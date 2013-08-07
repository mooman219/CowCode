package com.gmail.mooman219.module.region.store;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.ConfigJackson;
import com.gmail.mooman219.frame.serialize.jack.FastChunkLocation;
import com.gmail.mooman219.layout.ModuleType;

public class StoreChunk extends ConfigJackson {
    private static ChunkConfigData data;

    public StoreChunk() {
        super(ModuleType.REGION.getCast(), ModuleType.REGION.getDirectory(), "chunks", "data");
    }

    public static ChunkConfigData getData() {
        return data;
    }

    public static HashMap<FastChunkLocation, UUID> getChunks() {
        return data.chunkMap;
    }

    @Override
    public void onLoad(File file) {
        data = JsonHelper.fromJackson(file, ChunkConfigData.class);
        data.chunkMap.clear();
        for(FastChunkRegion chunk : data.chunks) {
            data.chunkMap.put(chunk.getChunk(), chunk.getUUID());
        }
    }

    @Override
    public void onSave(File file) {
        data.chunks.clear();
        for(FastChunkLocation chunk : data.chunkMap.keySet()) {
            data.chunks.add(new FastChunkRegion(chunk, data.chunkMap.get(chunk)));
        }
        try {
            JsonHelper.getFancyJackson().writeValue(file, data);
        } catch(JsonGenerationException e) {
            e.printStackTrace();
        } catch(JsonMappingException e) {
            e.printStackTrace();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void validateData() {
        if(data == null) {
            data = new ChunkConfigData();
        }
    }

    public static class ChunkConfigData {
        public ArrayList<FastChunkRegion> chunks = new ArrayList<FastChunkRegion>();
        public transient HashMap<FastChunkLocation, UUID> chunkMap = new HashMap<FastChunkLocation, UUID>();

        public ChunkConfigData() {}
    }
}
