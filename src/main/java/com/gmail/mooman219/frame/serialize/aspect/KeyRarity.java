package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.module.item.api.aspect.Rarity;

public class KeyRarity extends AspectKey<Rarity> {
    public KeyRarity(String name, Rarity defaultValue) {
        super("§η", name, defaultValue);
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
                setValue(Rarity.fromString(value));
            } catch(Exception e) {
                setValue(getDefaultValue());
            }
            return true;
        }
        return false;
    }
}
