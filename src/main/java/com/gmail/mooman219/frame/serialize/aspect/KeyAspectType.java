package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.module.item.api.aspect.ItemType;

public class KeyAspectType extends AspectKey<ItemType> {
    public KeyAspectType(String name, ItemType defaultValue) {
        super("§ΰ", name, defaultValue);
    }

    @Override
    public String write() {
        return getName() + getValue().getFullName();
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            try {
                String value = line.substring(getName().length());
                setValue(ItemType.fromString(value));
            } catch(Exception e) {
                setValue(getDefaultValue());
            }
            return true;
        }
        return false;
    }
}
