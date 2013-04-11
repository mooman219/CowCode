package com.gmail.mooman219.module.region.store;

public enum RegionCombatType {
    SAFE(0),
    CONTESTED(1),
    CHAOTIC(2);

    public final int id;

    RegionCombatType(int id) {
        this.id = id;
    }

    public static RegionCombatType getID(int id) {
        for(RegionCombatType regionCombatType : RegionCombatType.values()) {
            if(regionCombatType.id == id) {
                return regionCombatType;
            }
        }
        return SAFE;
    }
}
