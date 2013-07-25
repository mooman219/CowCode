package com.gmail.mooman219.module.region.type;

import java.io.IOException;

import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.text.Chat;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public enum RegionCombatType {
    SAFE(0, "Safe", Chat.GREEN + "" + Chat.BOLD + "Safe"),
    CONTESTED(1, "Contested", Chat.YELLOW + "" + Chat.BOLD + "Contested"),
    CHAOTIC(2, "Chaotic", Chat.RED + "" + Chat.BOLD + "Chaotic");

    private final int id;
    private final String name;
    private final String coloredName;

    RegionCombatType(int id, String name, String coloredName) {
        this.id = id;
        this.name = name;
        this.coloredName = coloredName;
    }

    public static RegionCombatType fromID(int id) {
        for(RegionCombatType regionCombatType : RegionCombatType.values()) {
            if(regionCombatType.id == id) {
                return regionCombatType;
            }
        }
        return SAFE;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getColoredName() {
        return coloredName;
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
            int id = NumberHelper.toInt(reader.nextString());
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
