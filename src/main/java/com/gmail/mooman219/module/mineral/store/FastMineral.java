package com.gmail.mooman219.module.mineral.store;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.frame.serialize.jack.FastLocation;
import com.gmail.mooman219.layout.JacksonData;

public class FastMineral implements JacksonData {
    private static final long serialVersionUID = -1980651915117679148L;
    private transient long respawnTime = 0;
    private FastLocation location;
    private Material type;
    private int respawnDelay;

    protected FastMineral() {}

    public FastMineral(Block block, int respawnDelay) {
        this(block.getLocation(), block.getType(), respawnDelay);
    }

    public FastMineral(Location location, Material type, int respawnDelay) {
        this.location = new FastLocation(location);
        this.type = type;
        this.respawnDelay = respawnDelay;
        this.respawnTime = 0;
    }

    /**
     * Getters and Setters
     */

    public long getRespawnTime() {
        return respawnTime;
    }

    public Material getType() {
        return type;
    }

    public int getRespawnDelay() {
        return respawnDelay;
    }

    public FastLocation getLocation() {
        return location;
    }

    public void setRespawnTime(long respawnTime) {
        this.respawnTime = respawnTime;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public void setRespawnDelay(int respawnDelay) {
        this.respawnDelay = respawnDelay;
    }

    /**
     * Misc functions
     */

    public Location toLocation() {
        return location.toLocation();
    }

    public boolean isSimilar(Location location) {
        return this.location.isSimilar(location);
    }

    public boolean isSimilar(Block block) {
        return this.location.isSimilar(block.getLocation());
    }

    public boolean hasTimeExpired(long currentTime) {
        return currentTime - respawnTime > 0;
    }

    public void resetTime(long currentTime) {
        respawnTime = currentTime + respawnDelay;
    }

    /**
     * Serialization and Deserialization
     */

    @Override
    public String serialize() {
        return JsonHelper.toJackson(this);
    }

    public static FastMineral deserialize(String data) {
        return JsonHelper.fromJackson(data, FastMineral.class);
    }

    /**
     * HashCode and Equals
     */

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
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
        FastMineral other = (FastMineral) obj;
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
}