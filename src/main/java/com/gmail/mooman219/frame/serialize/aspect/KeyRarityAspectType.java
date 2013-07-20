package com.gmail.mooman219.frame.serialize.aspect;

import com.gmail.mooman219.frame.item.AspectType;
import com.gmail.mooman219.frame.item.Rarity;

public class KeyRarityAspectType extends AspectKey<String> {
    private final String seperator;
    private final Rarity defaultRarity;
    private final AspectType defaultAspectType;
    private Rarity rarity;
    private AspectType aspectType;

    public KeyRarityAspectType(String name, String seperator, Rarity rarity, AspectType aspectType) {
        super(name, rarity.getFullName() + seperator + aspectType.getFullName());
        this.seperator = seperator;
        this.defaultRarity = rarity;
        this.defaultAspectType = aspectType;
        this.rarity = rarity;
        this.aspectType = aspectType;
    }

    public String getSeperator() {
        return seperator;
    }

    public Rarity getDefaultRarity() {
        return defaultRarity;
    }

    public AspectType getDefaultAspectType() {
        return defaultAspectType;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public AspectType getAspectType() {
        return aspectType;
    }

    public void setRarity(Rarity rarity) {
        this.rarity = rarity;
        setValue(rarity.getFullName() + seperator + aspectType.getFullName());
    }

    public void setAspectType(AspectType aspectType) {
        this.aspectType = aspectType;
        setValue(rarity.getFullName() + seperator + aspectType.getFullName());
    }

    @Override
    public String write() {
        return getName() + getValue();
    }

    @Override
    public boolean read(String line) {
        if(match(line)) {
            setRarity(defaultRarity);
            setAspectType(defaultAspectType);
            try {
                String value = line.substring(getName().length());
                if(value.length() > 0) {
                    String[] values = value.split(seperator, 2);
                    if(values.length > 0) {
                        setRarity(Rarity.fromString(values[0]));
                    }
                    if(values.length > 1) {
                        setAspectType(AspectType.fromString(values[1]));
                    }
                }
            } catch(Exception e) {}
            return true;
        }
        return false;
    }
}
