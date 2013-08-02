package com.gmail.mooman219.frame.serialize.aspect;

public class KeyString extends AspectKey<String> {
    public KeyString(String name, String defaultValue) {
        super("§κ", name, defaultValue);
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            setValue(getDefaultValue());
            try {
                String value = line.substring(getName().length());
                setValue(value.length() > 0 ? value : getDefaultValue());
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
