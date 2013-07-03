package com.gmail.mooman219.module.mineral.store;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.json.BasicLocation;
import com.gmail.mooman219.layout.JsonData;

public class BasicMineral implements JsonData {
    private final BasicLocation location;
    private Material type;
    private int respawnDelay;
    private transient long respawnTime = 0;

    public BasicMineral(Block block, int respawnDelay) {
        this(block.getLocation(), block.getType(), respawnDelay, -1L);
    }

    public BasicMineral(Location location, Material type, int respawnDelay, long respawnTime) {
        this.location = new BasicLocation(location);
        this.type = type;
        this.respawnDelay = respawnDelay;
        this.respawnTime = respawnTime;
    }

    public boolean match(Location location) {
        return this.location.match(location);
    }

    public boolean hasTimeExpired(long currentTime) {
        return currentTime - respawnTime > 0;
    }

    public void resetTime(long currentTime) {
        respawnTime = currentTime + respawnDelay;
    }

    public Location getLocation() {
        return location.toLocation();
    }

    public int getRespawnDelay() {
        return respawnDelay;
    }

    public long getRespawnTime() {
        return respawnTime;
    }
    
    public Material getType() {
        return this.type;
    }
    
    public void setType(Material material) {
        this.type = material;
    }

    public void setRespawnDelay(int delay) {
        respawnDelay = delay;
    }

    public void setRespawnTime(long time) {
        respawnTime = time;
    }

    /**
     * Json methods
     */

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }

    public static BasicMineral fromString(String string) {
        return JsonHelper.getGson().fromJson(string, BasicMineral.class);
    }

    /**
     * Needed methods
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((location == null) ? 0 : location.hashCode());
        result = prime * result + respawnDelay;
        result = prime * result + (int) (respawnTime ^ (respawnTime >>> 32));
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
        BasicMineral other = (BasicMineral) obj;
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
        if (respawnTime != other.respawnTime) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        return true;
    }
}
