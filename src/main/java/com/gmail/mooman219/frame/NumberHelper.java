package com.gmail.mooman219.frame;

import java.util.Random;
import java.util.UUID;

public class NumberHelper {
    public static final Random random = new Random();
    
    public static int floor(double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor - (int) (Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int ceil(final double num) {
        final int floor = (int) num;
        return floor == num ? floor : floor + (int) (~Double.doubleToRawLongBits(num) >>> 63);
    }

    public static int round(double num) {
        return floor(num + 0.5d);
    }

    public static int toInt(Object object) {
        if (object instanceof Number) {
            return ((Number) object).intValue();
        }
        try {
            return Integer.valueOf(object.toString());
        } catch (Exception e) {}
        return 0;
    }

    public static float toFloat(Object object) {
        if (object instanceof Number) {
            return ((Number) object).floatValue();
        }
        try {
            return Float.valueOf(object.toString());
        } catch (Exception e) {}
        return 0;
    }

    public static double toDouble(Object object) {
        if (object instanceof Number) {
            return ((Number) object).doubleValue();
        }
        try {
            return Double.valueOf(object.toString());
        } catch (Exception e) {}
        return 0;
    }

    public static long toLong(Object object) {
        if (object instanceof Number) {
            return ((Number) object).longValue();
        }
        try {
            return Long.valueOf(object.toString());
        } catch (Exception e) {}
        return 0;
    }

    public static short toShort(Object object) {
        if (object instanceof Number) {
            return ((Number) object).shortValue();
        }
        try {
            return Short.valueOf(object.toString());
        } catch (Exception e) {}
        return 0;
    }

    public static byte toByte(Object object) {
        if (object instanceof Number) {
            return ((Number) object).byteValue();
        }
        try {
            return Byte.valueOf(object.toString());
        } catch (Exception e) {}
        return 0;
    }
    
    public static UUID nextUUID() {
        return new UUID(random.nextLong(), random.nextLong());
    }
}
