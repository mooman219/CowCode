package com.gmail.mooman219.frame.serialize.aspect;

public class KeyString extends AspectKey<String> {
    public KeyString(String name, String defaultValue) {
        super(name, defaultValue);
    }

    @Override
    public String write() {
        return getName() + getValue();
    }

    @Override
    public void read(String line) {
        if(match(line)) {
            setValue(getDefaultValue());
            try {
                String value = line.substring(getName().length());
                setValue(value.length() > 0 ? value : getDefaultValue());
            } catch(Exception e) {}
        }
    }
}
