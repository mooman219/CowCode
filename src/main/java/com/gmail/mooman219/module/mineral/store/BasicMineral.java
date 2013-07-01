package com.gmail.mooman219.module.mineral.store;

import org.bukkit.Chunk;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import com.gmail.mooman219.bull.CDChunk;
import com.gmail.mooman219.frame.WorldHelper;
import com.gmail.mooman219.frame.serialize.JsonHelper;
import com.gmail.mooman219.layout.JsonData;

public class BasicMineral implements JsonData {
    public final int x;
    public final int y;
    public final int z;
    public Material type;
    public int respawnDelay;
    public long respawnTime;

    public BasicMineral(Block block, int respawnDelay) {
        this(block.getLocation(), block.getType(), respawnDelay);
    }

    public BasicMineral(Location location, Material type, int respawnDelay) {
        this(location.toVector(), type, respawnDelay);
    }

    public BasicMineral(Vector vector, Material type, int respawnDelay) {
        this(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), type, respawnDelay, -1L);
    }

    public BasicMineral(int x, int y, int z, Material type, int respawnDelay, long respawnTime) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.type = type;
        this.respawnDelay = respawnDelay;
        this.respawnTime = respawnTime;
    }

    public boolean match(Block block) {
        return match(block.getLocation());
    }

    public boolean match(Location location) {
        return match(location.toVector());
    }

    public boolean match(Vector vector) {
        return match(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ());
    }

    public boolean match(int x, int y, int z) {
        return this.x == x && this.y == y && this.z == z;
    }

    public Block getBlock(CDChunk chunk) {
        return chunk.getChunk().getWorld().getBlockAt(x, y, z);
    }

    public Block getBlock(Chunk chunk) {
        return chunk.getWorld().getBlockAt(x, y, z);
    }

    public Location getLocation(CDChunk chunk) {
        return new Location(chunk.getChunk().getWorld(), x, y, z);
    }

    public Location getLocation(Chunk chunk) {
        return new Location(chunk.getWorld(), x, y, z);
    }

    public void tick(Chunk chunk, long time) {
        if(respawnTime > -1 && time - respawnTime > 0) {
            revert(chunk);
        }
    }

    public void revert(Chunk chunk) {
        Block block = getBlock(chunk);
        WorldHelper.playEffect(block.getLocation(), Effect.MOBSPAWNER_FLAMES);
        block.setType(type);
        respawnTime = -1;
    }

    public void mine(Chunk chunk) {
        respawnTime = System.currentTimeMillis() + respawnDelay;
        getBlock(chunk).setType(Material.COBBLESTONE);
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
        result = prime * result + respawnDelay;
        result = prime * result + (int) (respawnTime ^ (respawnTime >>> 32));
        result = prime * result + ((type == null) ? 0 : type.hashCode());
        result = prime * result + x;
        result = prime * result + y;
        result = prime * result + z;
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
        if (respawnDelay != other.respawnDelay) {
            return false;
        }
        if (respawnTime != other.respawnTime) {
            return false;
        }
        if (type != other.type) {
            return false;
        }
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        if (z != other.z) {
            return false;
        }
        return true;
    }
}
