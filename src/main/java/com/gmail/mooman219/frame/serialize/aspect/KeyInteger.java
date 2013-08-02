package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.math.NumberHelper;

public class KeyInteger extends AspectKey<Integer> {
    public KeyInteger(String name, int defaultValue) {
        super("§ε", name, defaultValue);
    }


    @Override
    public boolean read(String line) {
        if(match(line)) {
            setValue(getDefaultValue());
            try {
                setValue(NumberHelper.toInt(line.substring(getName().length()), getDefaultValue()));
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
