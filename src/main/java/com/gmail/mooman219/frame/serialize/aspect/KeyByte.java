package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.NumberHelper;

public class KeyByte extends AspectKey<Byte> {
    public KeyByte(String name, byte defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String write() {
        return getName() + getValue();
    }

    @Override
    public void read(String line) {
        if(match(line)) {
            try {
                setValue(NumberHelper.toByte(line.substring(getName().length()), getDefaultValue()));
            } catch(Exception e) {
                setValue(getDefaultValue());
            }
        }
    }
}
