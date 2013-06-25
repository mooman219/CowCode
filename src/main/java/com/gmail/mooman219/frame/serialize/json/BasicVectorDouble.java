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

public class BasicVectorDouble {
    private double x = 0D;
    private double y = 0D;
    private double z = 0D;

    public BasicVectorDouble(Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    @Override
    public String toString() {
        return BasicVectorDouble.getGson().toJson(this);
    }

    public static BasicVectorDouble fromString(String string) {
        return BasicVectorDouble.getGson().fromJson(string, BasicVectorDouble.class);
    }

    public static Gson getGson() {
        return new GsonBuilder()
        .registerTypeAdapter(BasicVectorDouble.class, BasicVectorDouble.getAdapter())
        .create();
    }

    public static VectorDoubleAdapter getAdapter() {
        return new VectorDoubleAdapter();
    }

    public static class VectorDoubleAdapter extends TypeAdapter<BasicVectorDouble> {
        @Override
        public BasicVectorDouble read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return null;
            }
            String xyz = reader.nextString();
            String[] parts = xyz.split(",");
            return new BasicVectorDouble(new Vector(MathHelper.toDouble(parts[0]), MathHelper.toDouble(parts[1]), MathHelper.toDouble(parts[2])));
        }

        @Override
        public void write(JsonWriter writer, BasicVectorDouble value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            String xyz = value.x + "," + value.y + "," + value.z;
            writer.value(xyz);
        }
    }
}
