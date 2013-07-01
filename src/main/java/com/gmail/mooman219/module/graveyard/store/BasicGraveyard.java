package com.gmail.mooman219.module.graveyard.store;

import org.bukkit.Location;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicRichLocation;
import com.gmail.mooman219.layout.JsonData;
import com.google.gson.annotations.SerializedName;

public class BasicGraveyard implements JsonData {
    @SerializedName("Location") private final BasicRichLocation location;
    @SerializedName("Level_Requirement") private int levelRequirement = 0;

    public BasicGraveyard(Location location, int levelRequirement) {
        this.location = new BasicRichLocation(location);
        this.levelRequirement = levelRequirement;
    }

    public Location getLocation() {
        return location.toLocation();
    }

    public BasicRichLocation getBasicLocation() {
        return location;
    }

    public int getLevelRequirement() {
        return levelRequirement;
    }

    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicGraveyard fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicGraveyard.class);
    }

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + levelRequirement;
        result = prime * result + ((location == null) ? 0 : location.hashCode());
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
        BasicGraveyard other = (BasicGraveyard) obj;
        if (levelRequirement != other.levelRequirement) {
            return false;
        }
        if (location == null) {
            if (other.location != null) {
                return false;
            }
        } else if (!location.equals(other.location)) {
            return false;
        }
        return true;
    }
}
