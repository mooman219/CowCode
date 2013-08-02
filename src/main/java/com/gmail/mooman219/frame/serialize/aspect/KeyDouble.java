package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.math.NumberHelper;

public class KeyDouble extends AspectKey<Double> {
    public KeyDouble(String name, double defaultValue) {
        super("§γ", name, defaultValue);
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            setValue(getDefaultValue());
            try {
                setValue(NumberHelper.toDouble(line.substring(getName().length()), getDefaultValue()));
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
