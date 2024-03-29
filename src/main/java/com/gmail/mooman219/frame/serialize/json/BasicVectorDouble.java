package com.gmail.mooman219.frame.serialize.json;

import java.io.IOException;

import org.bukkit.util.Vector;

import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JsonData;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class BasicVectorDouble implements JsonData {
    private final double x;
    private final double y;
    private final double z;

    public BasicVectorDouble(Vector vec) {
        this.x = vec.getX();
        this.y = vec.getY();
        this.z = vec.getZ();
    }

    public Vector toVector() {
        return new Vector(x, y, z);
    }

    public boolean match(Vector vector) {
        return vector.getX() == x && vector.getY() == y && vector.getZ() == z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    /**
     * Json Methods
     */

    @Override
    public String toString() {
        return  JsonHelper.toJson(this);
    }

    public static BasicVectorDouble fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicVectorDouble.class);
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
            return new BasicVectorDouble(new Vector(NumberHelper.toDouble(parts[0]), NumberHelper.toDouble(parts[1]), NumberHelper.toDouble(parts[2])));
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

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(z);
        result = prime * result + (int) (temp ^ (temp >>> 32));
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
        BasicVectorDouble other = (BasicVectorDouble) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }
}
