package com.gmail.mooman219.frame.serialize.json;

import java.io.IOException;

import org.bukkit.util.Vector;

import com.gmail.mooman219.frame.MathHelper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class BasicVectorInteger {
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public BasicVectorInteger(Vector vec) {
        this.x = vec.getBlockX();
        this.y = vec.getBlockY();
        this.z = vec.getBlockZ();
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public static BasicVectorInteger fromString(String string) {
        return BasicVectorInteger.getGson().fromJson(string, BasicVectorInteger.class);
    }

    public static Gson getGson() {
        return new GsonBuilder()
        .registerTypeAdapter(BasicVectorInteger.class, BasicVectorInteger.getAdapter())
        .create();
    }

    public static VectorIntegerAdapter getAdapter() {
        return new VectorIntegerAdapter();
    }

    public static class VectorIntegerAdapter extends TypeAdapter<BasicVectorInteger> {
        @Override
        public BasicVectorInteger read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            String xyz = reader.nextString();
            String[] parts = xyz.split(",");
            return new BasicVectorInteger(new Vector(MathHelper.toInt(parts[0]), MathHelper.toInt(parts[1]), MathHelper.toInt(parts[2])));
        }

        @Override
        public void write(JsonWriter writer, BasicVectorInteger value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            String xyz = value.x + "," + value.y + "," + value.z;
            writer.value(xyz);
        }
    }
}
