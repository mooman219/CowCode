package com.gmail.mooman219.frame.serialize.aspect;

public class KeyBoolean extends AspectKey<Boolean> {
    public KeyBoolean(String name, Boolean defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String write() {
        if(getValue()) {            
            return getName();
        }
        return " ";
    }

    @Override
    public void read(String line) {
        setValue(match(line));
    }
}
