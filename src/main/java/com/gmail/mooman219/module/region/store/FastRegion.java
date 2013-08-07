package com.gmail.mooman219.module.region.store;
import java.util.UUID;

import com.gmail.mooman219.frame.math.NumberHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JacksonData;
import com.gmail.mooman219.module.region.type.RegionCombatType;

public class FastRegion implements JacksonData {
    private static final long serialVersionUID = -6234418878993669735L;
    private UUID uuid; // This identifies the region, it's paired with the chunk, don't ever fuck with it
    private String id;
    private String name = "DefaultName";
    private String description = "Default description.";
    private boolean isLocked = false;
    private RegionCombatType combatType = RegionCombatType.SAFE;

    protected FastRegion() {}

    public FastRegion(String uuid, String id, String name) {
        this(UUID.fromString(uuid), id, name);
    }

    public FastRegion(String id, String name) {
        this(NumberHelper.nextUUID(), id, name);
    }

    public FastRegion(UUID uuid, String id, String name) {
        this.uuid = uuid;
        this.id = id.toLowerCase();
        this.name = name;
    }

    /**
     * Getters and Setters
     */

    public RegionCombatType getCombatType() {
        return combatType;
    }

    public boolean isLocked() {
        return isLocked;
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

    public UUID getUUID() {
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

    public void setLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Serialization and Deserialization
     */

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastRegion deserialize(String data) {
        return JsonHelper.fromJackson(data, FastRegion.class);
    }

    /**
     * HashCode and Equals
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((combatType == null) ? 0 : combatType.hashCode());
        result = prime * result + ((description == null) ? 0 : description.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (isLocked ? 1231 : 1237);
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
        FastRegion other = (FastRegion) obj;
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
        if (isLocked != other.isLocked) {
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
}