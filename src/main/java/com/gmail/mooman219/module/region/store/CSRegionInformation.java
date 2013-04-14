package com.gmail.mooman219.module.region.store;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.gmail.mooman219.module.region.RegionCombatType;

@SerializableAs(value = "CSRegionInformation")
public class CSRegionInformation implements ConfigurationSerializable {
    public final String uuid;
    //
    public String name;
    public String id;
    public String information;
    public RegionCombatType combatType;

    public CSRegionInformation(String id, String name) {
        this(RegionCombatType.SAFE, id, name, "Default information");
    }
    
    public CSRegionInformation(RegionCombatType combatType, String id, String name, String information) {
        this.uuid = UUID.randomUUID().toString();
        this.id = id.toLowerCase();
        this.combatType = combatType;
        this.name = name;
        this.information = information;
    }

    public CSRegionInformation(Map<String, Object> map) {
        this.uuid = (String) map.get("uuid");
        this.name = (String) map.get("name");
        this.id = ((String) map.get("id")).toLowerCase();
        this.information = (String) map.get("information");
        this.combatType = RegionCombatType.getID((int) map.get("combattype"));
    }

    public static CSRegionInformation deserialize(Map<String, Object> map) {
        return new CSRegionInformation(map);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uuid", uuid);
        map.put("name", name);
        map.put("id", id);
        map.put("information", information);
        map.put("combattype", combatType.id);
        return map;
    }
}
