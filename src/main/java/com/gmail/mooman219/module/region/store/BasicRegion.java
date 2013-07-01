package com.gmail.mooman219.module.region.store;
import com.gmail.mooman219.frame.MathHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JsonData;

public class BasicRegion implements JsonData {
    // This identifies the region, don't fuck with it
    private final String uuid;

    private String id;
    private String name = "DefaultName";
    private String description = "Default description.";
    private boolean isLocked = false;
    private RegionCombatType combatType = RegionCombatType.SAFE;

    public BasicRegion(String id, String name) {
        this(MathHelper.nextUUID().toString(), id, name);
    }

    public BasicRegion(String uuid, String id, String name) {
        this.uuid = uuid;

        this.id = id.toLowerCase();
        this.name = name;
    }

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

    public String getUUID() {
        return uuid;
    }

    public BasicRegion setCombatType(RegionCombatType combatType) {
        this.combatType = combatType;
        return this;
    }

    public BasicRegion setDescription(String description) {
        this.description = description;
        return this;
    }

    public BasicRegion setID(String id) {
        this.id = id;
        return this;
    }

    public BasicRegion setLocked(boolean isLocked) {
        this.isLocked = isLocked;
        return this;
    }

    public BasicRegion setName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicRegion fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicRegion.class);
    }

    /**
     * Needed methods
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
        BasicRegion other = (BasicRegion) obj;
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