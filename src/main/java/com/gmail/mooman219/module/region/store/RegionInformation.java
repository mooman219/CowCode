package com.gmail.mooman219.module.region.store;

import java.io.Serializable;
import java.util.UUID;

import com.gmail.mooman219.frame.NumberHelper;

public class RegionInformation implements Serializable {
	private static final long serialVersionUID = -8236674287606087142L;
	
	private final UUID key;
    private int combatTypeID = RegionCombatType.SAFE.id;
    private String name = "Default";
    private String description = "This is the default region information.";
    
    private transient RegionCombatType combatType = null;
    
    public RegionInformation(UUID key) {
        this.key = key;
    }
    
    public RegionInformation() {
        this.key = NumberHelper.nextUUID();
    }
    
    public RegionCombatType getCombatType() {
        if(combatType == null) {
            combatType = RegionCombatType.getID(combatTypeID);
        }
        return combatType;
    }
    
    public RegionInformation setCombatType(RegionCombatType combatType) {
        this.combatType = combatType;
        this.combatTypeID = combatType.id;
        return this;
    }
    
    public String getName() {
        return name;
    }
    
    public RegionInformation setName(String name) {
        this.name = name;
        return this;
    }
    
    public String getDescription() {
        return description;
    }
    
    public RegionInformation setDescription(String description) {
        this.description = description;
        return this;
    }

    public UUID getKey() {
        return key;
    }
}
