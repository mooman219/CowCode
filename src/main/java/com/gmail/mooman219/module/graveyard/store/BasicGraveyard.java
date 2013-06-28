package com.gmail.mooman219.module.graveyard.store;

import org.bukkit.Location;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicRichLocation;
import com.google.gson.annotations.SerializedName;

public class BasicGraveyard {
    @SerializedName("Location") public final BasicRichLocation location;
    @SerializedName("Level_Requirement") public int levelRequirement = 0;

    public BasicGraveyard(Location location, int levelRequirement) {
        this.location = new BasicRichLocation(location);
        this.levelRequirement = levelRequirement;
    }

    public final Location getLocation() {
        return location.toLocation();
    }

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicGraveyard fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicGraveyard.class);
    }
}
