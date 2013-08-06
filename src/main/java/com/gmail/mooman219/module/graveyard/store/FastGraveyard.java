package com.gmail.mooman219.module.graveyard.store;

import org.bukkit.Location;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.FastRichLocation;
import com.gmail.mooman219.layout.JacksonData;

public class FastGraveyard implements JacksonData {
    private static final long serialVersionUID = 5054385065265203214L;
    private FastRichLocation location;
    private int level = 0;

    protected FastGraveyard() {}

    public FastGraveyard(Location location, int level) {
        this.location = new FastRichLocation(location);
        this.level = level;
    }

    public FastRichLocation getLocation() {
        return location;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Location toLocation() {
        return location.toLocation();
    }

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastGraveyard deserialize(String data) {
        return JsonHelper.fromJackson(data, FastGraveyard.class);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + level;
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
        FastGraveyard other = (FastGraveyard) obj;
        if (level != other.level) {
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
