package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.math.NumberHelper;

public class KeyIntegerRange extends AspectKey<String> {
    private final String seperator;
    private final int defaultMin;
    private final int defaultMax;
    private int min;
    private int max;

    public KeyIntegerRange(String name, String seperator, int min, int max) {
        super("§ζ", name, min + seperator+ max);
        this.seperator = seperator;
        this.defaultMin = min;
        this.defaultMax = max;
        this.min = min;
        this.max = max;
    }

    public String getSeperator() {
        return seperator;
    }

    public int getDefaultMin() {
        return defaultMin;
    }

    public int getDefaultMax() {
        return defaultMax;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    public void setMin(int min) {
        this.min = min;
        setValue(min + seperator+ max);
    }

    public void setMax(int max) {
        this.max = max;
        setValue(min + seperator+ max);
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            setMin(defaultMin);
            setMax(defaultMax);
            try {
                String value = line.substring(getName().length());
                if(value.length() > 0) {
                    String[] values = value.split(seperator, 2);
                    if(values.length > 0) {
                        setMin(NumberHelper.toInt(values[0], 1));
                    }
                    if(values.length > 1) {
                        setMax(NumberHelper.toInt(values[1], 2));
                    }
                }
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
