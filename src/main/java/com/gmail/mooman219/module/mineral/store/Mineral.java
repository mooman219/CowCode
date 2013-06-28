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

public class Mineral {
    public final int x;
    public final int y;
    public final int z;
    public Material type;
    public int respawnDelay;
    public long respawnTime;

    public Mineral(Block block, int respawnDelay) {
        this(block.getLocation(), block.getType(), respawnDelay);
    }

    public Mineral(Location location, Material type, int respawnDelay) {
        this(location.toVector(), type, respawnDelay);
    }

    public Mineral(Vector vector, Material type, int respawnDelay) {
        this(vector.getBlockX(), vector.getBlockY(), vector.getBlockZ(), type, respawnDelay, -1L);
    }

    public Mineral(int x, int y, int z, Material type, int respawnDelay, long respawnTime) {
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
        return chunk.chunk.getWorld().getBlockAt(x, y, z);
    }

    public Block getBlock(Chunk chunk) {
        return chunk.getWorld().getBlockAt(x, y, z);
    }

    public Location getLocation(CDChunk chunk) {
        return new Location(chunk.chunk.getWorld(), x, y, z);
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

    @Override
    public String toString() {
        return JsonHelper.toJson(this);
    }
}
