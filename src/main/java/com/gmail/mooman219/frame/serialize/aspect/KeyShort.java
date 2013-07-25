package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.math.NumberHelper;

public class KeyShort extends AspectKey<Short> {
    public KeyShort(String name, short defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String write() {
        return getName() + getValue();
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            setValue(getDefaultValue());
            try {
                setValue(NumberHelper.toShort(line.substring(getName().length()), getDefaultValue()));
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
