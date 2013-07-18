package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.item.AspectType;

public class KeyAspectType extends AspectKey<AspectType> {
    public KeyAspectType(String name, AspectType defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String write() {
        return getName() + getValue().getName();
    }

    @Override
    public void read(String line) {
        if(match(line)) {
            try {
                String value = line.substring(getName().length());
                setValue(AspectType.fromString(value));
            } catch(Exception e) {
                setValue(getDefaultValue());
            }
        }
    }
}
