package com.gmail.mooman219.module.region.store;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.TagHelper;
import com.gmail.mooman219.module.region.store.RegionCombatType;

@SerializableAs(value = "CSRegionInformation")
public class CSRegionInfo implements ConfigurationSerializable {
    // These identify the region, don't fuck with them
    private final String uuid;

    private String id;
    private String name = "DefaultName";
    private String description = "Default description.";
    private RegionCombatType combatType = RegionCombatType.SAFE;

    public CSRegionInfo(String id, String name) {
        this.uuid = MathHelper.nextUUID().toString();
        this.id = id.toLowerCase();

        this.name = name;
    }

    public CSRegionInfo(Map<String, Object> map) {
        this.uuid = TagHelper.getValue(map, "uuid", MathHelper.nextUUID().toString());
        this.id = TagHelper.getValue(map, "id", "tmp" + uuid); // The fallback should be renamed later

        this.name = TagHelper.getValue(map, "name", name);
        this.description = TagHelper.getValue(map, "description", description);
        this.combatType = RegionCombatType.getID(TagHelper.getValue(map, "combattype", combatType.id));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((combatType == null) ? 0 : combatType.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        CSRegionInfo other = (CSRegionInfo) obj;
        if (combatType != other.combatType) {
            return false;
        }
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (uuid == null) {
            if (other.uuid != null) {
                return false;
            }
        } else if (!uuid.equals(other.uuid)) {
            return false;
        }
        return true;
    }

    public static CSRegionInfo deserialize(Map<String, Object> map) {
        return new CSRegionInfo(map);
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

    public RegionCombatType getCombatType() {
        return combatType;
    }

    public String getDescription() {
        return description;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUUID() {
        return uuid;
    }

    public void setCombatType(RegionCombatType combatType) {
        this.combatType = combatType;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setID(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}