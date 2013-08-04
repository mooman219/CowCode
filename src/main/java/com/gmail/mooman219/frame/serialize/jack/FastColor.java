package com.gmail.mooman219.frame.serialize.jack;

import java.lang.ref.WeakReference;
import org.bukkit.Color;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JacksonData;

public class FastColor implements JacksonData {
    private static final long serialVersionUID = 1192644931243147486L;
    private int red;
    private int blue;
    private int green;

    private transient WeakReference<Color> weakColor;

    protected FastColor() {}

    public FastColor(Color color) {
        this.red = color.getRed();
        this.blue = color.getBlue();
        this.green = color.getGreen();
    }
    public int getRed() {
        return red;
    }

    public int getBlue() {
        return blue;
    }

    public int getGreen() {
        return green;
    }

    public Color toColor() {
        if (weakColor == null || weakColor.get() == null) {
            weakColor = new WeakReference<Color>(Color.fromRGB(red, green, blue));
        }
        return weakColor.get();
    }

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastColor deserialize(String data) {
        return JsonHelper.fromJackson(data, FastColor.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + blue;
        result = prime * result + green;
        result = prime * result + red;
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
        FastColor other = (FastColor) obj;
        if (blue != other.blue) {
            return false;
        }
        if (green != other.green) {
            return false;
        }
        if (red != other.red) {
            return false;
        }
        return true;
    }
}
