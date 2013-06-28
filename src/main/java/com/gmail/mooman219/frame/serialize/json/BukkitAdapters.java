package com.gmail.mooman219.frame.serialize.json;

import java.io.IOException;

import org.bukkit.Material;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class BukkitAdapters {
    public static MaterialAdapter getMaterialAdapter() {
        return new MaterialAdapter();
    }

    public static class MaterialAdapter extends TypeAdapter<Material> {
        @Override
        public Material read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            int id = reader.nextInt();
            return Material.getMaterial(id);
        }

        @Override
        public void write(JsonWriter writer, Material value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            int id = value.getId();
            writer.value(id);
        }
    }
}
