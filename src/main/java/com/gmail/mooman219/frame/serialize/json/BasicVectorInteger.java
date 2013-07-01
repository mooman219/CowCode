package com.gmail.mooman219.frame.serialize.json;

import java.io.IOException;

import org.bukkit.util.Vector;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class BasicVectorInteger {
    private int x = 0;
    private int y = 0;
    private int z = 0;

    public BasicVectorInteger(Vector vec) {
        this(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
    }

    public BasicVectorInteger(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return  JsonHelper.toJson(this);
    }

    public static BasicVectorInteger fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicVectorInteger.class);
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

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BasicVectorInteger other = (BasicVectorInteger) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }
}
