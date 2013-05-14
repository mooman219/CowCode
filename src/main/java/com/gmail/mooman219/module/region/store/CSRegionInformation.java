package com.gmail.mooman219.module.region.store;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.module.region.store.RegionCombatType;

@SerializableAs(value = "CSRegionInformation")
public class CSRegionInformation implements ConfigurationSerializable {
    // These identify the region, don't fuck with them
    public final String uuid;
    public final String id;

    public String name = "DefaultName";
    public String description = "Default description.";
    public RegionCombatType combatType = RegionCombatType.SAFE;

    public CSRegionInformation(String id, String name) {
        this.uuid = MathHelper.nextUUID().toString();
        this.id = id.toLowerCase();

        this.name = name;
    }

    public CSRegionInformation(Map<String, Object> map) {
        this.uuid = TagHelper.getValue(map, "uuid", MathHelper.nextUUID().toString());
        this.id = TagHelper.getValue(map, "id", "tmp" + uuid); // The fallback should be renamed later

        this.name = TagHelper.getValue(map, "name", name);
        this.description = TagHelper.getValue(map, "description", description);
        this.combatType = RegionCombatType.getID(TagHelper.getValue(map, "combattype", combatType.id));
    }

    public static CSRegionInformation deserialize(Map<String, Object> map) {
        return new CSRegionInformation(map);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("uuid", uuid);
        map.put("id", id);

        map.put("name", name);
        map.put("description", description);
        map.put("combattype", combatType.id);
        return map;
    }
}