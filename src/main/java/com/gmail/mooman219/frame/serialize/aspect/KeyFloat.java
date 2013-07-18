package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.NumberHelper;

public class KeyFloat extends AspectKey<Float> {
    public KeyFloat(String name, float defaultValue) {
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
                setValue(NumberHelper.toFloat(line.substring(getName().length()), getDefaultValue()));
            } catch(Exception e) {
                setValue(getDefaultValue());
            }
        }
    }
}
