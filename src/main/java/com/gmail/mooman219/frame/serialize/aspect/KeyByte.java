package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.math.NumberHelper;

public class KeyByte extends AspectKey<Byte> {
    public KeyByte(String name, byte defaultValue) {
        super("§β", name, defaultValue);
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            setValue(getDefaultValue());
            try {
                setValue(NumberHelper.toByte(line.substring(getName().length()), getDefaultValue()));
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
