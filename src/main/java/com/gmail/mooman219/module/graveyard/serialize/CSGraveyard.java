package com.gmail.mooman219.module.graveyard.serialize;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.gmail.mooman219.frame.serialize.CSLocation;

@SerializableAs(value = "CSGraveyard")
public class CSGraveyard implements ConfigurationSerializable{
    public final CSLocation location;
    //
    public int levelRequirement = 0;

    public CSGraveyard(Location location, int levelRequirement) {
        this.location = new CSLocation(location);
        this.levelRequirement = levelRequirement;
    }

    public CSGraveyard(Map<String, Object> map) {
        this.location = (CSLocation) map.get("location");
        this.levelRequirement = (Integer)map.get("level");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = CSGraveyard.class.hashCode();
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + levelRequirement;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CSGraveyard other = (CSGraveyard) obj;
        if (location == null) {
            if (other.location != null)
                return false;
        } else if (!location.equals(other.location))
            return false;
        if (levelRequirement != other.levelRequirement)
            return false;
        return true;
    }

    public static CSGraveyard deserialize(Map<String, Object> map) {
        return new CSGraveyard(map);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("location", location);
        map.put("level", levelRequirement);
        return map;
    }

    public final Location getLocation() {
        return location.getLocation();
    }
}
