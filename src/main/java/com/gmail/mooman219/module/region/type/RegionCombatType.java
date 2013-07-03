package com.gmail.mooman219.module.region.type;

import java.io.IOException;

import com.gmail.mooman219.frame.MathHelper;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public enum RegionCombatType {
    SAFE(0),
    CONTESTED(1),
    CHAOTIC(2);

    public final int id;

    RegionCombatType(int id) {
        this.id = id;
    }

    public static RegionCombatType fromID(int id) {
        for(RegionCombatType regionCombatType : RegionCombatType.values()) {
            if(regionCombatType.id == id) {
                return regionCombatType;
            }
        }
        return SAFE;
    }

    public static RegionCombatTypeAdapter getAdapter() {
        return new RegionCombatTypeAdapter();
    }

    public static class RegionCombatTypeAdapter extends TypeAdapter<RegionCombatType> {
        @Override
        public RegionCombatType read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            int id = MathHelper.toInt(reader.nextString());
            return RegionCombatType.fromID(id);
        }

        @Override
        public void write(JsonWriter writer, RegionCombatType value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            String id = value.id + "";
            writer.value(id);
        }
    }
}
