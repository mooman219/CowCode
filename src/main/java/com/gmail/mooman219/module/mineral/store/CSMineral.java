package com.gmail.mooman219.module.mineral.store;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import com.gmail.mooman219.frame.serialize.CSBasicLocation;

@SerializableAs(value = "CSMineral")
public class CSMineral implements ConfigurationSerializable{
    public final CSBasicLocation location;
    //
    public Material type;
    public int respawnDelay;
    // Temp
    public transient int timeLeft = 0;

    public CSMineral(Location location, Material type, int respawnDelay) {
        this.location = new CSBasicLocation(location);
        this.type = type;
        this.respawnDelay = respawnDelay;
    }

    public CSMineral(Map<String, Object> map) {
        this.location = (CSBasicLocation) map.get("location");
        this.type = Material.getMaterial((String)map.get("type"));
        this.respawnDelay = (Integer)map.get("delay");
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = CSMineral.class.hashCode();
        result = prime * result + ((location == null) ? 0 : location.hashCode());
        result = prime * result + respawnDelay;
        result = prime * result + ((type == null) ? 0 : type.hashCode());
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
        CSMineral other = (CSMineral) obj;
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        if (respawnDelay != other.respawnDelay) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }

    public static CSMineral deserialize(Map<String, Object> map) {
        return new CSMineral(map);
    }

    @Override
    public Map<String, Object> serialize() {
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("location", location);
        map.put("type", type.name());
        map.put("delay", respawnDelay);
        return map;
    }

    public final Location getLocation() {
        return location.getLocation();
    }
}
