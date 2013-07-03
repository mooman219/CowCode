package com.gmail.mooman219.bull;

import java.lang.ref.SoftReference;
import java.util.ArrayList;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.CraftChunk;
import org.bukkit.entity.Entity;

import com.gmail.mooman219.craftbukkit.BullData;
import com.gmail.mooman219.handler.config.ConfigGlobal;
import com.gmail.mooman219.module.mineral.store.BasicMineral;
import com.gmail.mooman219.module.region.RegionManager;
import com.gmail.mooman219.module.region.store.BasicRegion;

public class CDChunk extends BullData {
    private final Chunk chunk;

    private CDChunk(Chunk chunk) {
        this.chunk = chunk;
    }

    /**
     * Variables
     */

    // Saved
    private CDChunkData data = new CDChunkData();
    private static class CDChunkData {
        public String parentUUID = "";
        public ArrayList<BasicMineral> minerals = new ArrayList<BasicMineral>();
    }
    // Unsaved
    private SoftReference<BasicRegion> softParentInfo;
    private byte tick = 0;
    private long lastActive = Long.MAX_VALUE;

    /*
     * Live
     */

    public BasicRegion getRegion() {
        if(softParentInfo == null || softParentInfo.get() == null) {
            setRegion(RegionManager.getInfo(data.parentUUID));
        }
        return softParentInfo.get();
    }

    public void setRegion(BasicRegion info) {
        softParentInfo = new SoftReference<BasicRegion>(info);
        data.parentUUID = info.getUUID();
    }

    public void tick() {
        long time = System.currentTimeMillis();
        tick = 0;
        // Minerals!
        for(BasicMineral mineral : data.minerals) {
            mineral.tick(chunk, time);
        }
        // Chunk unloading stuff
        if(time - lastActive > ConfigGlobal.bull.chunk.chunkUnloadDelay) {
            CDChunk.unload(chunk);
        }
    }

    /*
     * Event
     */

    @Override
    public void onGet() {
        this.lastActive = System.currentTimeMillis();
    }

    @Override
    public void onTick() {
        if(tick >= ConfigGlobal.bull.chunk.chunkTickPeriod) {
            tick();
        }
        tick++;
    }

    /*
     * Default
     */

    public Chunk getChunk() {
        return chunk;
    }

    public net.minecraft.server.Chunk getHandle() {
        return ((CraftChunk)chunk).getHandle();
    }

    public static CDChunk get(Block block) {
        return get(block.getChunk());
    }

    public static CDChunk get(Location location) {
        return get(location.getChunk());
    }

    public static CDChunk get(Entity entity) {
        return get(entity.getLocation().getChunk());
    }

    public static CDChunk get(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.bull_live == null) {
            handle.bull_live = new CDChunk(chunk);
        }
        CDChunk cdChunk = (CDChunk) handle.bull_live;
        cdChunk.onGet();
        return cdChunk;
    }

    /**
     * This method will save any of the CDChunk's data then remove it from the chunk object.
     * This should be called when the chunk unloads to prevent the data from presisting.
     */
    public static void unload(Chunk chunk) {
        net.minecraft.server.Chunk handle = ((CraftChunk)chunk).getHandle();
        if(handle.bull_live != null) {
            handle.bull_live = null;
        }
    }
}
