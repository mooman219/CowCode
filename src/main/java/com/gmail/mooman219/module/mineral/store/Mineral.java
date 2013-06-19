package com.gmail.mooman219.module.mineral.store;

import java.util.ArrayList;

import net.minecraft.server.NBTTagCompound;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import com.gmail.mooman219.bullbukkit.CDChunk;
import com.gmail.mooman219.frame.TagHelper;

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
    
    public Location getLocation(CDChunk chunk) {
        return new Location(chunk.chunk.getWorld(), x, y, z);
    }
    
    public Location getLocation(Chunk chunk) {
        return new Location(chunk.getWorld(), x, y, z);
    }

    public void tick(Chunk chunk, long time) {
        if(respawnTime != -1 && time - respawnTime > 0) {            
            revert(chunk);
        }
    }

    public void revert(Chunk chunk) {
        chunk.getWorld().getBlockAt(x, y, z).setType(type);
        respawnTime = -1;
    }

    public void mine(Chunk chunk) {
        respawnTime = System.currentTimeMillis() + respawnDelay;
    }

    public NBTTagCompound toCompound() {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setIntArray("loc", new int[] {x, y, z});
        tag.setInt("resdelay", respawnDelay);
        tag.setLong("restime", respawnTime);
        TagHelper.setMaterial(tag, "material", type);
        return tag;
    }

    public static Mineral fromCompound(NBTTagCompound tag) {
        int[] loc = TagHelper.getIntArray(tag, "loc", null);
        int respawnDelay = TagHelper.getInt(tag, "resdelay", 0);
        long respawnTime = TagHelper.getLong(tag, "restime", 0l);
        Material type = TagHelper.getMaterial(tag, "material", null);
        if(loc == null || type == null || respawnDelay == 0 || loc.length < 3) {
            return null;
        }
        return new Mineral(loc[0], loc[1], loc[2], type, respawnDelay, respawnTime);
    }

    public static ArrayList<Mineral> fromCompoundList(NBTTagCompound tag) {
        ArrayList<Mineral> ret = new ArrayList<Mineral>();
        short total = TagHelper.getShort(tag, "size", (short)0);
        if(total == 0) {
            return ret;
        }
        for(int i = 0; i < total; i++) {
            Mineral found = fromCompound(tag.getCompound("m" + i));
            if(found == null) {
                continue;
            }
            ret.add(found);
        }
        return ret;
    }

    public static NBTTagCompound toCompoundList(ArrayList<Mineral> list) {
        NBTTagCompound tag = new NBTTagCompound();
        short total = (short) list.size();
        if(total == 0) {
            return tag;
        }
        tag.setShort("size", total);
        for(int i = 0; i < total; i++) {
            tag.setCompound("m" + i, list.get(i).toCompound());
        }
        return tag;
    }
}
